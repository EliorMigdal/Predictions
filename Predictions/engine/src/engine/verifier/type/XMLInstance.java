package engine.verifier.type;

import engine.jaxb.generated.PRDWorld;
import engine.verifier.exception.FileDoesNotExist;
import engine.verifier.exception.FileIsNotXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class XMLInstance {
    public XMLInstance() {
    }

    private boolean validateFile(String XMLPath)
            throws FileDoesNotExist, FileIsNotXML {
        File xmlFile = new File(XMLPath);

        if (!xmlFile.exists()) {
            throw new FileDoesNotExist(XMLPath);
        }

        Path path = Paths.get(XMLPath);
        String fileExtension = path.getFileName().toString();

        if (!fileExtension.endsWith(".xml")) {
            throw new FileIsNotXML(XMLPath);
        }

        return true;
    }

    public PRDWorld initWorld(String XMLFilePath)
            throws JAXBException, FileIsNotXML, FileDoesNotExist {
        PRDWorld world = null;
        if (validateFile(XMLFilePath)) {
            final String JAXB_XML_PACKAGE_NAME = "engine.jaxb.generated";
            File XMLFile = new File(XMLFilePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            world = (PRDWorld) jaxbUnmarshaller.unmarshal(XMLFile);
        }

        return world;
    }
}