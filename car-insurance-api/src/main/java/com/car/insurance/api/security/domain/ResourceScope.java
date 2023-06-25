package com.car.insurance.api.security.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResourceScope {

	@EmbeddedId
	private ResourceScopeId id;

	@ManyToOne
	@MapsId("resource_id")
	public Resource resource;

	@ManyToOne
	@MapsId("scope_id")
	public Scope scope;

	public ResourceScope(Resource resource, Scope scope) {
		this.id = new ResourceScopeId(resource.getId(), scope.getId());
		this.resource = resource;
		this.scope = scope;
	}
}
