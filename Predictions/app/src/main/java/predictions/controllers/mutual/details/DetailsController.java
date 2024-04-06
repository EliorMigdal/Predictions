package predictions.controllers.mutual.details;

import dto.response.mutual.details.properties.PropertyDTO;
import dto.response.mutual.details.properties.PropertyResponseDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import predictions.controllers.mutual.MutualControllers;
import predictions.controllers.mutual.details.properties.PropertyGenerator;
import predictions.controllers.mutual.details.properties.controllers.ActionPropertyCardController;
import predictions.controllers.mutual.details.properties.controllers.PropertyCardController;
import predictions.controllers.mutual.details.tree.TreeGenerator;
import predictions.controllers.mutual.details.tree.node.TreeNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.IntStream;

public class DetailsController implements MutualControllers {
    private TreeView<TreeNode> elementTreeView;
    private VBox propertiesLayout;

    private final TreeGenerator treeGenerator = new TreeGenerator();
    private final PropertyGenerator propertyGenerator = new PropertyGenerator();
    private final SimpleStringProperty selectedSimulation = new SimpleStringProperty("");

    public void setElementTreeView(TreeView<TreeNode> elementTreeView) {
        this.elementTreeView = elementTreeView;
    }

    public void setPropertiesLayout(VBox propertiesLayout) {
        this.propertiesLayout = propertiesLayout;
    }

    public void initializeTreeView() {
        this.elementTreeView.setOnMouseClicked(event -> {
            if (!(event.getPickResult().getIntersectedNode() instanceof StackPane)) {
                try {
                    this.getElementData();
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
            }
        });

        this.elementTreeView.setDisable(true);
        this.propertiesLayout.setDisable(true);
    }

    public void showSimulationDetails(String simulationName) {
        TreeItem<TreeNode> treeRoot = treeGenerator.generateTree(simulationName);
        this.elementTreeView.setDisable(false);
        this.propertiesLayout.setDisable(false);
        this.elementTreeView.setRoot(treeRoot);
        this.selectedSimulation.set(simulationName);
    }

    private void getElementData() throws IOException {
        TreeNode selectedNode = elementTreeView.getSelectionModel().getSelectedItem().getValue();
        PropertyResponseDTO propertyDTO = propertyGenerator.generatePropertyCards(selectedNode, selectedSimulation.get());

        if (propertyDTO != null) {
            if (selectedNode.getNodeType().equals("ActionProperty")) {
                this.generateActionPropCards(propertyDTO);
            } else {
                this.generateRegularPropCards(propertyDTO);
            }
        }
    }

    public void clearDetailsTreeView() {
        elementTreeView.setRoot(null);
        elementTreeView.setDisable(true);
        propertiesLayout.getChildren().clear();
        propertiesLayout.setDisable(true);
    }

    private void generateActionPropCards(PropertyResponseDTO propertyDTO) throws IOException {
        propertiesLayout.getChildren().clear();
        HBox currHbox = new HBox();
        currHbox.setAlignment(Pos.CENTER);
        propertiesLayout.getChildren().add(currHbox);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Mutual/details/ActionPropertyCard.fxml"));
        Node loadComponent = loader.load();

        ActionPropertyCardController controller = loader.getController();
        ArrayList<Label> labelArray = controller.getLabelArray();

        IntStream.range(0, propertyDTO.getProperties().size())
                .forEach(index -> {
                    String key = propertyDTO.getProperties().get(index).getPropertyTitle();
                    String value = propertyDTO.getProperties().get(index).getPropertyValue();

                    controller.setPropertyID(labelArray.get((index + 1) * 2).getId(), key);
                    controller.setPropertyID(labelArray.get((index + 1) * 2 + 1).getId(), value);
                });

        currHbox.getChildren().add(loadComponent);
    }

    private void generateRegularPropCards(PropertyResponseDTO propertyDTO) throws IOException {
        propertiesLayout.getChildren().clear();
        HBox currHbox = new HBox(0);
        currHbox.setAlignment(Pos.CENTER);
        currHbox.setPadding(new Insets(0, 0, 0, 50));
        propertiesLayout.getChildren().add(currHbox);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Mutual/details/PropertyCard.fxml"));
        Node loadComponent = loader.load();

        PropertyCardController controller = loader.getController();
        Iterator<PropertyDTO> entryIterator = propertyDTO.getProperties().iterator();

        IntStream.range(0, 3).forEach(i -> {
            if (entryIterator.hasNext()) {
                PropertyDTO entry = entryIterator.next();
                controller.setProperty(i, entry.getPropertyTitle(), entry.getPropertyValue());
            }
        });

        currHbox.getChildren().add(loadComponent);
    }
}
