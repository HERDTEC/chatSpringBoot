package com.example.demo.db;

import com.example.demo.chat.ChatChangesListener;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Nicolas on 8/21/2016.
 *
 * Initializing the DB
 * For this little rethinkdb.chat, we will need a database called rethinkdb.chat and a table called messages.
 * To avoid creating them by hand, we can create a Spring bean that will get called when the application starts.
 *
 */
public class DbInitializer implements InitializingBean{

    @Autowired
    private RethinkDBConnectionFactory connectionFactory;

    @Autowired
    private ChatChangesListener chatChangesListener;

    private static final RethinkDB r = RethinkDB.r;

    @Override
    public void afterPropertiesSet() throws Exception {
        createDB();
        chatChangesListener.pushChangesToWebSocket();
    }

    private void createDB() throws TimeoutException{
        Connection connection = null;
        connection = connectionFactory.createConnection();
        List<String> dbList = r.dbList().run(connection);

        if(!dbList.contains("log")){
            r.dbCreate("log").run(connection);
        }

        List<String> tables = r.db("log").tableList().run(connection);
        if(!tables.contains("messages")){
            r.db("log").tableCreate("messages").run(connection);
            r.db("log").table("messages").indexCreate("time").run(connection);
        }
    }
}