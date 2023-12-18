package com.cliche.cliche.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @NotNull
    private String id;

    @NotNull
    @Indexed(unique = true)
    private String email;

    @NotNull
    private String password;

	public User(@NotNull String email, @NotNull String password) {
		super();
		this.email = email;
		this.password = password;
	}
}