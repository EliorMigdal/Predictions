package predictions.controllers.mutual.details.tree.node;

import javafx.scene.control.TreeItem;

import java.util.ArrayList;

public class TreeNode extends TreeItem<TreeNode> {
    private final String nodeName;
    private final String nodeType;
    private final Integer index;
    private final ArrayList<String> hierarchy;

    public TreeNode(String nodeName, String nodeType, Integer nodeIndex) {
        this.nodeName = nodeName;
        this.nodeType = nodeType;
        this.index = nodeIndex;
        this.hierarchy = new ArrayList<>();
    }

    public void addHierarchy(ArrayList<String> parentHierarchy) {
        this.hierarchy.addAll(parentHierarchy);
        this.hierarchy.add(nodeName);
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public Integer getIndex() {
        return index;
    }

    public ArrayList<String> getHierarchy() {
        return hierarchy;
    }

    @Override
    public String toString() {
        return nodeName;
    }
}