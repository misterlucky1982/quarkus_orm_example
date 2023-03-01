package nby.misterlucky.learning.quarkus.domain.entity;

import nby.misterlucky.learning.quarkus.domain.OrderStatus;
import nby.misterlucky.learning.quarkus.util.OrderStatusConverter;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntityBase {
    @Id
    @SequenceGenerator(
            name = "orderSequence",
            sequenceName = "orderId_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSequence")
    public Long id;

    @Column(length = 40)
    public String item;

    @Column
    public Long price;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonbTransient
    public Customer customer;

    @Column
    @Convert(converter = OrderStatusConverter.class)
    public OrderStatus status;
}
