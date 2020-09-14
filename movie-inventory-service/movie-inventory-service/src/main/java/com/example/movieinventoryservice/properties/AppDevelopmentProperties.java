package com.example.movieinventoryservice.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.development")
public class AppDevelopmentProperties {

	private String title;
	private String project;
	private String version;
	private String environment;
	private String licence;
	private String contact;
	private String description;

	public AppDevelopmentProperties() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppDevelopmentProperties(String title, String project, String version, String environment, String licence,
			String contact, String description) {
		super();
		this.title = title;
		this.project = project;
		this.version = version;
		this.environment = environment;
		this.licence = licence;
		this.contact = contact;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "AppDevelopmentProperties [title=" + title + ", project=" + project + ", version=" + version
				+ ", environment=" + environment + ", licence=" + licence + ", contact=" + contact + ", description="
				+ description + "]";
	}

}
