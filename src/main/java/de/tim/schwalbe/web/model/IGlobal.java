package de.tim.schwalbe.web.model;

import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * @author juri.ritter
 *         <p>
 *         Copyright 2015 mindbit GmbH, Inc. All rights reserved
 */
public interface IGlobal {

        public static final int MAX_PAGINATOR_ENTRIES = 10;

        /**
         * @return the timestamp of the update.
         */
        Timestamp getUpdatedAt();

        /**
         * @return the timestamp of the update.
         */
        String getPrettyUpdatedAt();

        /**
         * @param updatedAt The timestamp of the update.
         */
        void setUpdatedAt();

        /**
         * @return the creation timestamp.
         */
        Timestamp getCreatedAt();

        /**
         * @return the creation timestamp.
         */
        String getPrettyCreatedAt();

        /**
         * @param creationAt The timestamp of the update.
         */
        void setCreatedAt();

        /**
         * JSON representation of an object
         *
         * @return JSON Object
         */
        JSONObject toJson();

}
