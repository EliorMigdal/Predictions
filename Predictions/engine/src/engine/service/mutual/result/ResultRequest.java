package engine.service.mutual.result;

import engine.service.mutual.MutualRequest;

public interface ResultRequest extends MutualRequest {
    Object generateResponse(Object... args);
}
