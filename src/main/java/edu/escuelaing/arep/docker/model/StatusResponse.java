package edu.escuelaing.arep.docker.model;

public enum StatusResponse {
    SUCCESS("Success"), ERROR("Error");

    final private String status;

    /**
     * @param status asignar estado de la respuesta
     */
    StatusResponse(String status) {
        this.status = status;
    }

    /**
     * @return el estado de la respuesta
     */
    public String getStatus() {
        return status;
    }

}
