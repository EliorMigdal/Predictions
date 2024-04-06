package engine.verifier.type;

import engine.jaxb.generated.*;
import engine.verifier.exception.*;
import engine.verifier.type.action.VerifyAction;
import engine.verifier.type.action.exception.ActionException;
import engine.verifier.type.grid.VerifyGrid;
import engine.verifier.type.grid.exception.InvalidGrid;

import javax.xml.bind.JAXBException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class XMLChecker {
    private final XMLInstance instance;

    public XMLChecker() {
        this.instance = new XMLInstance();
    }

    public PRDWorld performChecks(String XMLPath)
            throws EnvPropertyDuplicationXML, EntityPropertyDuplicationXML, EntityReferenceError,
            FileIsNotXML, FileDoesNotExist, JAXBException, InvalidGrid, RuleException {
        Optional<PRDWorld> world = Optional.ofNullable(instance.initWorld(XMLPath));

        if (world.isPresent()) {
            new VerifyGrid().verifyGrid(world.get().getPRDGrid());
            checkPRDEnvironment(world.get().getPRDEnvironment());
            checkPRDEntity(world.get().getPRDEntities());

            VerifyAction verifyAction = new VerifyAction();

            for (PRDRule rule : world.get().getPRDRules().getPRDRule()) {
                for (PRDAction action : rule.getPRDActions().getPRDAction()) {
                    try {
                        verifyAction.checkAction(action, world.get().getPRDEntities(),
                                world.get().getPRDEnvironment());
                    } catch (ActionException exception) {
                        throw new RuleException(rule.getName(), exception);
                    }
                }
            }

            return world.get();
        } else {
            return null;
        }
    }

    private void checkPRDEnvironment(PRDEnvironment prdEnvironment)
            throws EnvPropertyDuplicationXML {
        Set<String> uniqueNames = new HashSet<>();
        for (PRDEnvProperty property : prdEnvironment.getPRDEnvProperty()) {
            if (!uniqueNames.add(property.getPRDName())) {
                 throw new EnvPropertyDuplicationXML(property.getPRDName());
            }
        }

    }

    private void checkPRDEntity(PRDEntities prdEntities)
            throws EntityPropertyDuplicationXML {
         Optional<PRDEntity> invalidEntity = prdEntities.getPRDEntity().stream()
                .filter(entity -> entity.getPRDProperties().getPRDProperty().stream()
                        .map(PRDProperty::getPRDName)
                        .distinct()
                        .count() != entity.getPRDProperties().getPRDProperty().size())
                .findFirst();
         if (invalidEntity.isPresent()) {
             String invalidEntityName = invalidEntity.get().getName();

            Optional<String> invalidPropName =  invalidEntity.get().getPRDProperties().getPRDProperty().stream()
                     .collect(Collectors.groupingByConcurrent(PRDProperty::getPRDName))
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getValue().size() > 1)
                            .map(Map.Entry::getKey)
                            .findFirst();
            if(invalidPropName.isPresent()){
                throw new EntityPropertyDuplicationXML(invalidEntityName,invalidPropName.get());
            }
         }
    }
}