package com.example.demo.db;


import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;


import java.util.concurrent.TimeoutException;

public class RethinkDBConnectionFactory {
    private String host;
    private String db;
    private Integer port;

    public RethinkDBConnectionFactory(String host, Integer port, String db) {
        this.host = host;
        this.db = db;
        this.port = port;
    }

    public Connection createConnection() {
       //  RethinkDB.r.connection().hostname(host).connect();
         return RethinkDB.r.connection()
        	    .hostname(host)
        	    .port(port)
        	    .db(db)
        	    .connect();
    }
}
