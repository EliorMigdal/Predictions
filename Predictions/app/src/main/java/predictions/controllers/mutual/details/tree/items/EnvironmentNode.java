package predictions.controllers.mutual.details.tree.items;

import dto.response.mutual.details.tree.EnvironmentDetailsDTO;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import predictions.controllers.mutual.details.tree.node.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class EnvironmentNode {
    public TreeItem<TreeNode> generateEnvironmentTree(ArrayList<String> hierarchy,
                                                      ArrayList<EnvironmentDetailsDTO> environmentDetails) {
        final List<ImageView> imageViewList = getImageViews();

        TreeNode environmentNode = new TreeNode("Environment", "Environments", -1);
        environmentNode.addHierarchy(hierarchy);
        TreeItem<TreeNode> environmentRoot = new TreeItem<>(environmentNode, imageViewList.get(0));

        environmentDetails.forEach(environmentProperty -> {
            TreeNode childEnvironmentNode = new TreeNode(environmentProperty.getEnvironmentPropertyName(),
                    "EnvironmentProperty", -1);
            childEnvironmentNode.addHierarchy(environmentNode.getHierarchy());

            ImageView childEnvironmentImage = new ImageView(imageViewList.get(1).getImage());
            fitImage(childEnvironmentImage);
            TreeItem<TreeNode> newPropertyBranch = new TreeItem<>(childEnvironmentNode, childEnvironmentImage);
            environmentRoot.getChildren().add(newPropertyBranch);
        });

        return environmentRoot;
    }

    private List<ImageView> getImageViews() {
        List<ImageView> imageViewList = Arrays.asList(
                new ImageView(new Image("predictions/controllers/mutual/details/tree/images/environmentVar.png")),
                new ImageView(new Image("predictions/controllers/mutual/details/tree/images/propertyImg.png"))
        );

        imageViewList.forEach(this::fitImage);

        return imageViewList;
    }

    private void fitImage(ImageView image) {
        image.setFitWidth(40);
        image.setFitHeight(40);
    }
}