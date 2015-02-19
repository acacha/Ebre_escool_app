package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.pojos;

import com.google.gson.annotations.Expose;
/**
 * Created by criminal on 12/02/15.
 */
  public class Result {

        @Expose
        private String id;
        @Expose
        private String message;

        /**
         *
         * @return
         * The id
         */
        public String getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The message
         */
        public String getMessage() {
            return message;
        }

        /**
         *
         * @param message
         * The message
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }




