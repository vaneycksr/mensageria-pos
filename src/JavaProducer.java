import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JavaProducer implements Runnable {

    public void run() {
        try { // Create a connection factory.
            ActiveMQConnectionFactory factory =
                    new ActiveMQConnectionFactory("tcp://localhost:61616");

            //Create connection.
            Connection connection = factory.createConnection();

            // Start the connection
            connection.start();

            // Create a session which is non transactional
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create Destination queue
            Destination queue = session.createQueue("br.paciente");

            // Create a producer
            MessageProducer producer = session.createProducer(queue);

            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            String msg = "Enviando menssagem";

            Paciente p = new Paciente();

            p.setId(1);
            p.setNome("Olá!");

            // insert message
            //TextMessage message = session.createTextMessage(msg);
            ObjectMessage objectMessage = session.createObjectMessage(p);

            System.out.println("Deu Certo!!");
           // producer.send(message);
            producer.send(objectMessage);

            session.close();
            connection.close();
        }
        catch (Exception ex) {
            System.out.println("Exception Occured");
        }
    }
}