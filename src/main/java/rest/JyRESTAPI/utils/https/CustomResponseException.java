package rest.JyRESTAPI.utils.https;

import lombok.Getter;

@Getter
public class CustomResponseException extends RuntimeException {
    private static final long serialVersionUID = 8760381557784086238L;
    private final Response response;

    public CustomResponseException( String message , int code) {
        this.response = Response.builder(message , code).build();
    }
}