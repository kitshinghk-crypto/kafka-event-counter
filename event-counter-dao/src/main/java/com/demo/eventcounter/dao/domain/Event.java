package com.demo.eventcounter.dao.domain;

import javax.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="event")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Event {
    @Id
    //@org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private String uuid;
    private String type;
    private String ip;
    private String url;
}
