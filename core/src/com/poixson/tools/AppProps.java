package com.poixson.tools;


public interface AppProps {



	public AppProperties getProperties();



	// -------------------------------------------------------------------------------
	// properties



	public default String getArtifact() {        return this.getProperties().artifact;        }
	public default String getGroup() {           return this.getProperties().group;           }
	public default String getSlug() {            return this.getProperties().slug;            }
	public default String getTitle() {           return this.getProperties().title;           }
	public default String getVersion() {         return this.getProperties().version;         }
	public default String getLicense() {         return this.getProperties().license;         }
	public default String getCommitHashFull() {  return this.getProperties().commitHashFull;  }
	public default String getCommitHashShort() { return this.getProperties().commitHashShort; }
	public default String getURL() {             return this.getProperties().url;             }
	public default String getOrgName() {         return this.getProperties().orgName;         }
	public default String getOrgURL() {          return this.getProperties().orgUrl;          }
	public default String getIssueName() {       return this.getProperties().issueName;       }
	public default String getIssueURL() {        return this.getProperties().issueUrl;        }



}
