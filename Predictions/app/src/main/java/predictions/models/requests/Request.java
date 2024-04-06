package predictions.models.requests;

import predictions.views.Enums.HttpMethods;

public interface Request {
    Object GET();
    Object POST();
    Object PUT();
    Object handleRequest(HttpMethods method);

}
