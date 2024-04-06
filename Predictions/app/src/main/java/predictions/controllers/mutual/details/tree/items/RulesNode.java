package predictions.controllers.mutual.details.tree.items;

import dto.response.mutual.details.tree.RulesDetailsDTO;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import predictions.controllers.mutual.details.tree.node.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RulesNode {
    public TreeItem<TreeNode> generateRulesTree(ArrayList<String> hierarchy, ArrayList<RulesDetailsDTO> rulesDetails) {
        final List<ImageView> imageViewList = getImageViews();

        TreeNode ruleNode = new TreeNode("Rules", "Rules", -1);
        ruleNode.addHierarchy(hierarchy);
        TreeItem<TreeNode> ruleRoot = new TreeItem<>(ruleNode, imageViewList.get(0));

        rulesDetails.forEach(rule -> {
            TreeNode childRuleName = new TreeNode(rule.getRuleName(), "Rule", -1);
            childRuleName.addHierarchy(ruleNode.getHierarchy());

            ImageView childRuleImage = new ImageView(imageViewList.get(1).getImage());
            fitImage(childRuleImage);
            TreeItem<TreeNode> childRuleBranch = new TreeItem<>(childRuleName, childRuleImage);

            ruleRoot.getChildren().add(childRuleBranch);

            TreeNode actionsRootNode = new TreeNode("Actions", "ActionsRoot", -1);
            actionsRootNode.addHierarchy(childRuleBranch.getValue().getHierarchy());

            ImageView actionRootImage = new ImageView(imageViewList.get(2).getImage());
            fitImage(actionRootImage);
            TreeItem<TreeNode> actionsRoot = new TreeItem<>(actionsRootNode, actionRootImage);
            childRuleBranch.getChildren().add(actionsRoot);

            final int[] actionIndex = {0};

            rule.getActionDetails().forEach(action -> {
                TreeNode childActionName = new TreeNode(action.getActionName(),
                        "ActionProperty", actionIndex[0]);
                childActionName.addHierarchy(actionsRootNode.getHierarchy());

                ImageView actionChildImage = new ImageView(imageViewList.get(3).getImage());
                fitImage(actionChildImage);
                TreeItem<TreeNode> childActionBranch = new TreeItem<>(childActionName, actionChildImage);
                actionsRoot.getChildren().add(childActionBranch);
                actionIndex[0]++;
            });

            TreeNode activationRootNode = new TreeNode("Activation Terms",
                    "ActivationTermsRoot", -1);
            activationRootNode.addHierarchy(childRuleBranch.getValue().getHierarchy());

            ImageView activationRootImage = new ImageView(imageViewList.get(4).getImage());
            fitImage(activationRootImage);
            TreeItem<TreeNode> activationsRoot = new TreeItem<>(activationRootNode, activationRootImage);
            childRuleBranch.getChildren().add(activationsRoot);

            rule.getActivationDetails().forEach(activation -> {
                TreeNode childActivationNode = new TreeNode(activation.getActivationName(),
                        "ActivationProperty", -1);
                childActivationNode.addHierarchy(activationRootNode.getHierarchy());

                ImageView activationChildImage = new ImageView(imageViewList.get(5).getImage());
                fitImage(activationChildImage);
                TreeItem<TreeNode> childActivationBranch = new TreeItem<>(childActivationNode, activationChildImage);
                activationsRoot.getChildren().add(childActivationBranch);
            });
        });

        return ruleRoot;
    }

    private List<ImageView> getImageViews() {
        List<ImageView> imageViewList = Arrays.asList(
                new ImageView(new Image("predictions/controllers/mutual/details/tree/images/rulesImg.png")),
                new ImageView(new Image("predictions/controllers/mutual/details/tree/images/ruleImg.png")),
                new ImageView(new Image("predictions/controllers/mutual/details/tree/images/actionsImg.png")),
                new ImageView(new Image("predictions/controllers/mutual/details/tree/images/actionImg.png")),
                new ImageView(new Image("predictions/controllers/mutual/details/tree/images/activationTermsImg.png")),
                new ImageView(new Image("predictions/controllers/mutual/details/tree/images/activateImg.png"))
        );

        imageViewList.forEach(this::fitImage);

        return imageViewList;
    }

    private void fitImage(ImageView image) {
        image.setFitHeight(40);
        image.setFitWidth(40);
    }
}