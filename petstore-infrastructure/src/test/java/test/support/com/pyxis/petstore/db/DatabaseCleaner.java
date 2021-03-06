package test.support.com.pyxis.petstore.db;

import com.pyxis.petstore.domain.billing.PaymentMethod;
import com.pyxis.petstore.domain.order.LineItem;
import com.pyxis.petstore.domain.order.Order;
import com.pyxis.petstore.domain.product.Item;
import com.pyxis.petstore.domain.product.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DatabaseCleaner {

    private static final Class<?>[] ENTITY_TYPES = {
            LineItem.class,
            Order.class,
            PaymentMethod.class,
            Item.class,
            Product.class
    };
    private static final String[] SEQUENCE_NAMES = {
            "order_number_sequence"
    };

    private final Session session;

    public DatabaseCleaner(Session session) {
        this.session = session;
    }

    public void clean() {
        Transaction transaction = session.beginTransaction();
        for (Class<?> entityType : ENTITY_TYPES) {
            deleteEntities(entityType);
        }
        for (String sequenceName : SEQUENCE_NAMES) {
            resetSequence(sequenceName);
        }
        transaction.commit();
    }

    private void resetSequence(String sequenceName) {
        session.createSQLQuery("truncate " + sequenceName).executeUpdate();
    }

    private void deleteEntities(Class<?> entityType) {
        session.createQuery("delete from " + entityNameOf(entityType)).executeUpdate();
    }

    private String entityNameOf(Class<?> entityType) {
        return entityType.getName();
    }
}
