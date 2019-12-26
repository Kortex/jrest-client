package com.ariskourt.restclient.resources;

public class RestResponse<R, E> {

    private final Integer code;
    public Integer getCode() { return code; }

    private final boolean isSuccess;
    public boolean getSuccess() { return isSuccess; }

    private final R response;
    public R getResponse() { return response; }

    private final E error;
    public E getError() { return error; }

    private RestResponse(Integer code, boolean isSuccess, R response, E error) {
	this.code = code;
	this.isSuccess = isSuccess;
	this.response = response;
	this.error = error;
    }

    @Override
    public String toString() {
	return "RestResponse{" +
	    "code=" + code +
	    ", isSuccess=" + isSuccess +
	    ", response=" + response +
	    ", error=" + error +
	    '}';
    }

    public static <R, E> Builder<R, E> builder() {
	return new Builder<>();
    }

    public static class Builder<R, E> {

        private Integer code;
        private boolean isSuccess;
        private R response;
        private E error;

	public Builder<R, E> code(Integer code) {
            this.code = code;
            return this;
	}

	public Builder<R, E> isSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
            return this;
	}

	public Builder<R, E> response(R response) {
            this.response = response;
            return this;
	}

	public Builder<R, E> error(E error) {
            this.error = error;
            return this;
	}

	public RestResponse<R, E> build() {
            return new RestResponse<>(code, isSuccess, response, error);
	}

    }

}
