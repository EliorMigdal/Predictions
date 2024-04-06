package engine.service.mutual.details.propertycard;

import engine.service.mutual.MutualRequest;

public interface PropertyCardRequest extends MutualRequest {
    Object generateResponse(Object... args);
}