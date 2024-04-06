package utills;

import java.net.URL;

public class FXMLConstants {
    private static final String BASE_PACKAGE = "/Fxml";
    public static class ClientBundle {
        private static final String CLIENT_PACKAGE = "/Client";

        public static class resultsBundle {
            private static final String RESULTS_CLIENT_PACKAGE = "/Views/results/Views";

            private static final String IN_PROGRESS_SIMULATION_ITEM_IDENTIFIER =
                    BASE_PACKAGE + CLIENT_PACKAGE + RESULTS_CLIENT_PACKAGE +  "/leftscreen/inProgressSimulationListItem.fxml";

            public static final URL IN_PROGRESS_SIMULATION_ITEM_RESOURCE = FXMLConstants
                    .class
                    .getResource(IN_PROGRESS_SIMULATION_ITEM_IDENTIFIER);

            private static final String FINISHED_SIMULATION_ITEM_IDENTIFIER =
                    BASE_PACKAGE + CLIENT_PACKAGE + RESULTS_CLIENT_PACKAGE +  "/leftscreen/FinishedSimulationListItem.fxml";

            public static final URL FINISHED_SIMULATION_ITEM_RESOURCE = FXMLConstants
                    .class
                    .getResource(FINISHED_SIMULATION_ITEM_IDENTIFIER);

        }

        public static class executeBundle {
            private static final String EXECUTE_PACKAGE = "/Views/execution/Views";

            private static final String ENVIRONMENT_CARD_IDENTIFIER =
                    BASE_PACKAGE + CLIENT_PACKAGE + EXECUTE_PACKAGE + "/EnvironmentCard.fxml";

            public static final URL ENVIRONMENT_CARD_RESULT_RESOURCE = FXMLConstants
                    .class
                    .getResource(ENVIRONMENT_CARD_IDENTIFIER);

            private static final String ENTITY_CARD_IDENTIFIER =
                    BASE_PACKAGE + CLIENT_PACKAGE + EXECUTE_PACKAGE + "/EntityCard.fxml";

            public static final URL ENTITY_CARD_RESULT_RESOURCE = FXMLConstants
                    .class
                    .getResource(ENTITY_CARD_IDENTIFIER);

        }
        public static class requestsBundle {
            private static final String REQUEST_PACKAGE = "/Views/execution/Views";
            private static final String CELL_PACKAGE = "/Views/cells/popup";


            private static final String TERMINATION_TERM_POPUP_IDENTIFIER =
                    BASE_PACKAGE + CLIENT_PACKAGE + CELL_PACKAGE + "/terminationTermsPopup.fxml";

            public static final URL TERMINATION_TERM_POPUP_RESOURCE = FXMLConstants
                    .class
                    .getResource(TERMINATION_TERM_POPUP_IDENTIFIER);

        }
    }

    public static class AdminBundle {
        private static final String ADMIN_PACKAGE = "/Admin";

        public static class resultsBundle {
            private static final String RESULTS_ADMIN_PACKAGE = "/Views/execution_history/Views";

            private static final String ADMIN_FINISHED_SIMULATION_TILE_IDENTIFIER =
                    BASE_PACKAGE + ADMIN_PACKAGE + RESULTS_ADMIN_PACKAGE +  "/AdminFinishedSimulationTile.fxml";

            public static final URL ADMIN_FINISHED_SIMULATION_TILE_RESOURCE = FXMLConstants
                    .class
                    .getResource(ADMIN_FINISHED_SIMULATION_TILE_IDENTIFIER);
        }
    }


    public static class MutualBundle {
        private static final String MUTUAL_PACKAGE = "/Mutual";

        public static class detailsBundle {
            private static final String MUTUAL_DETAILS_PACKAGE = "/details";

            private static final String ACTION_PROPERTY_CARD_IDENTIFIER =
                    BASE_PACKAGE + MUTUAL_PACKAGE + MUTUAL_DETAILS_PACKAGE +  "/ActionPropertyCard.fxml";

            public static final URL ACTION_PROPERTY_CARD_RESOURCE = FXMLConstants
                    .class
                    .getResource(ACTION_PROPERTY_CARD_IDENTIFIER);


            private static final String PROPERTY_CARD_IDENTIFIER =
                    BASE_PACKAGE + MUTUAL_PACKAGE + MUTUAL_DETAILS_PACKAGE +  "/PropertyCard.fxml";

            public static final URL PROPERTY_CARD_RESOURCE = FXMLConstants
                    .class
                    .getResource(ACTION_PROPERTY_CARD_IDENTIFIER);

        }

        public static class resultsBundle {
            private static final String RESULTS_MUTUAL_PACKAGE = "/results";

            private static final String SIMULATION_ENDED_RESULTS_IDENTIFIER =
                    BASE_PACKAGE + MUTUAL_PACKAGE + RESULTS_MUTUAL_PACKAGE + "/bottomscreen/SimulationEndedResults.fxml";

            public static final URL SIMULATION_ENDED_RESULTS_RESOURCE = FXMLConstants
                    .class
                    .getResource(SIMULATION_ENDED_RESULTS_IDENTIFIER);

            private static final String ENTITY_DETAILS_PROPERTY_CARD_IDENTIFIER =
                    BASE_PACKAGE + MUTUAL_PACKAGE + RESULTS_MUTUAL_PACKAGE + "/upperscreen/entitypropertyDetailsCard.fxml";


            public static final URL ENTITY_DETAILS_PROPERTY_CARD_RESOURCE = FXMLConstants
                    .class
                    .getResource(ENTITY_DETAILS_PROPERTY_CARD_IDENTIFIER);

        }

    }

}
