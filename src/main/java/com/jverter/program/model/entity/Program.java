package com.jverter.program.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jverter.auth.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "program")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Program {
	@Id
	@Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = true)
	private String description;
	@Column(nullable = true)
	private String jsCode;
	
	@OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<InputField> inputFields;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

}
