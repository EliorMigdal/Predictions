package predictions.controllers.mutual.details.properties;

import dto.response.mutual.details.properties.PropertyResponseDTO;
import predictions.controllers.mutual.details.tree.node.TreeNode;
import predictions.httpclient.mutual.details.properties.*;

public final class PropertyGenerator {
    public PropertyResponseDTO generatePropertyCards(TreeNode selectedNode, String simulationName) {
        switch (selectedNode.getNodeType()) {
            case "Grid":
                return new GridPropertyRequest().getGridProperty(simulationName);
            case "EntityProperty":
                return new EntityPropertyRequest().getEntityProperty(simulationName,
                        selectedNode.getHierarchy().get(2), selectedNode.getNodeName());
            case "EnvironmentProperty":
                return new EnvironmentPropertyRequest().getEnvironmentProperty(simulationName,
                        selectedNode.getNodeName());
            case "ActionProperty":
                return new ActionPropertyRequest().getActionProperty(simulationName, selectedNode.getHierarchy().get(2),
                        selectedNode.getNodeName(), selectedNode.getIndex().toString());
            case "ActivationProperty":
                return new ActivationPropertyRequest().getActivationProperty(simulationName,
                        selectedNode.getHierarchy().get(2), selectedNode.getNodeName());
            default:
                return null;
        }
    }
}