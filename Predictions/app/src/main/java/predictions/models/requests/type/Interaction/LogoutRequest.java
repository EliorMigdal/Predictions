package predictions.models.requests.type.Interaction;

import predictions.models.requests.Request;
import predictions.views.Enums.HttpMethods;

import static predictions.models.requests.type.Interaction.LoginRequest.LoginLogoutHandler;

public class LogoutRequest implements Request {
    private final String userName;
    private  final String isClient;
    private final String endPoint;
    public LogoutRequest(String userName, String isClient, String endPoint) {
        this.endPoint = endPoint;
        this.userName = userName;
        this.isClient = isClient;

    }

    @Override
    public Object GET() {
        return null;
    }

    @Override
    public Object POST() {
        return LoginLogoutHandler(endPoint, userName, isClient);
    }

    @Override
    public Object PUT() {
        return null;
    }


    @Override
    public Object handleRequest(HttpMethods method) {
        switch (method){
            case GET:
                break;

            case POST:
                return POST();
            default:
                return "";
        }
        return null;
    }
}
