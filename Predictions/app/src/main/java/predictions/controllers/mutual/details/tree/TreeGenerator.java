package predictions.controllers.mutual.details.tree;

import dto.response.mutual.details.tree.TreeDetailsResponseDTO;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import predictions.controllers.mutual.details.tree.items.EntitiesNode;
import predictions.controllers.mutual.details.tree.items.EnvironmentNode;
import predictions.controllers.mutual.details.tree.items.RulesNode;
import predictions.controllers.mutual.details.tree.node.TreeNode;
import predictions.httpclient.mutual.details.tree.TreeNodesRequest;

import java.util.ArrayList;

public final class TreeGenerator {
    public TreeItem<TreeNode> generateTree(String simulationName) {
        ArrayList<String> hierarchy = new ArrayList<>();
        hierarchy.add("Simulation");
        ImageView simulationPic = new ImageView(new Image("predictions/controllers/mutual/" +
                "details/tree/images/simulationImg.png"));
        simulationPic.setFitWidth(40);
        simulationPic.setFitHeight(40);
        TreeItem<TreeNode> treeRoot = new TreeItem<>(
                new TreeNode("Simulation", "Simulation", -1), simulationPic);

        ImageView gridPic = new ImageView(new Image("predictions/controllers/mutual/" +
                "details/tree/images/worldImg.png"));
        gridPic.setFitWidth(40);
        gridPic.setFitHeight(40);
        TreeItem<TreeNode> gridNode = new TreeItem<>(new TreeNode("Grid", "Grid", -1),
                gridPic);
        treeRoot.getChildren().add(gridNode);

        TreeNodesRequest getTreeNode = new TreeNodesRequest();
        TreeDetailsResponseDTO treeDetails = getTreeNode.getTreeNodes(simulationName);

        TreeItem<TreeNode> entityNode = new EntitiesNode().generateEntitiesTree(hierarchy, treeDetails.getEntityDetails());
        treeRoot.getChildren().add(entityNode);

        TreeItem<TreeNode> rulesNode = new RulesNode().generateRulesTree(hierarchy, treeDetails.getRulesDetails());
        treeRoot.getChildren().add(rulesNode);

        TreeItem<TreeNode> environmentNode = new EnvironmentNode().generateEnvironmentTree(hierarchy,
                treeDetails.getEnvironmentDetails());
        treeRoot.getChildren().add(environmentNode);

        return treeRoot;
    }
}