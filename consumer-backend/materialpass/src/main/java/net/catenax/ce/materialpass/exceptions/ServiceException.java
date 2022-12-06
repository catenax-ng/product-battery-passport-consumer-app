package net.catenax.ce.materialpass.exceptions;

public class ServiceException extends RuntimeException{
        public ServiceException(String serviceName, String errorMessage) {
            super("["+serviceName+"] " + errorMessage);
        }
        public ServiceException(String serviceName,Exception e, String errorMessage) {
            super("["+serviceName+"] " + errorMessage+", "+e.getMessage());
        }

}