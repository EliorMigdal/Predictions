package predictions.controllers.mutual.details.tree.items;

import dto.response.mutual.details.tree.EntityDetailsDTO;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import predictions.controllers.mutual.details.tree.node.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class EntitiesNode {
    public TreeItem<TreeNode> generateEntitiesTree(ArrayList<String> hierarchy, ArrayList<EntityDetailsDTO> entityDetails) {
        final List<ImageView> imageViewList = getImageViews();
        TreeNode entityNode = new TreeNode("Entities", "Entities", -1);
        entityNode.addHierarchy(hierarchy);
        TreeItem<TreeNode> entityRoot = new TreeItem<>(entityNode, imageViewList.get(0));

        entityDetails.forEach(entity -> {
            TreeNode childEntityNode = new TreeNode(entity.getEntityName(), "Entity", -1);
            childEntityNode.addHierarchy(entityNode.getHierarchy());
            ImageView childEntityImage = new ImageView(imageViewList.get(1).getImage());
            fitImage(childEntityImage);
            TreeItem<TreeNode> childEntityBranch = new TreeItem<>(childEntityNode, childEntityImage);
            entityRoot.getChildren().add(childEntityBranch);

            entity.getEntityProperties().forEach(property -> {
                TreeNode childPropertyNode = new TreeNode(property.getPropertyName(), "EntityProperty", -1);
                childPropertyNode.addHierarchy(childEntityNode.getHierarchy());
                ImageView childPropertyImage = new ImageView(imageViewList.get(2).getImage());
                fitImage(childPropertyImage);
                TreeItem<TreeNode> childPropertyBranch = new TreeItem<>(childPropertyNode, childPropertyImage);
                childEntityBranch.getChildren().add(childPropertyBranch);
            });
        });

        return entityRoot;
    }

    private List<ImageView> getImageViews() {
        List<ImageView> imageViewList = Arrays.asList(
                new ImageView(new Image(("predictions/controllers/mutual/details/tree/images/entitiesImg.png"))),
                new ImageView(new Image(("predictions/controllers/mutual/details/tree/images/entityImg.png"))),
                new ImageView(new Image(("predictions/controllers/mutual/details/tree/images/propertyImg.png"))
        ));

        imageViewList.forEach(this::fitImage);

        return imageViewList;
    }

    private void fitImage(ImageView image) {
        image.setFitWidth(40);
        image.setFitHeight(40);
    }
}