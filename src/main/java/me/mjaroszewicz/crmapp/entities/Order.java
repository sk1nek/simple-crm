package me.mjaroszewicz.crmapp.entities;

import lombok.*;
import me.mjaroszewicz.crmapp.dto.OrderDto;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // -1 for accepted, 0 for in-progress, 1 for realized
    @NonNull private int state;

    @NonNull private String name;

    @NonNull private String description;

    @NonNull private double value;

    @ManyToOne
    @NonNull private Client recipient;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ElementCollection(targetClass = Payment.class)
    private List<Payment> payments;

    @NonNull private String dateReceived;

    @NonNull private String dateDeadline;

    private double paymentPercentage;

    public OrderDto getDto(){
        OrderDto ret = new OrderDto();

        ret.setClientId(getId());
        ret.setDateDeadline(dateDeadline);
        ret.setDescription(description);
        ret.setOrderName(name);
        ret.setValue(value);

        return ret;
    }
}
