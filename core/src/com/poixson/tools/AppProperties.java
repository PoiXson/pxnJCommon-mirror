package com.poixson.tools;


public interface AppProperties {



	public AppProps getProps();



	// -------------------------------------------------------------------------------
	// properties



	public default String getArtifact() {        return this.getProps().artifact;        }
	public default String getGroup() {           return this.getProps().group;           }
	public default String getSlug() {            return this.getProps().slug;            }
	public default String getTitle() {           return this.getProps().title;           }
	public default String getVersion() {         return this.getProps().version;         }
	public default String getLicense() {         return this.getProps().license;         }
	public default String getCommitHashFull() {  return this.getProps().commitHashFull;  }
	public default String getCommitHashShort() { return this.getProps().commitHashShort; }
	public default String getURL() {             return this.getProps().url;             }
	public default String getOrgName() {         return this.getProps().orgName;         }
	public default String getOrgURL() {          return this.getProps().orgUrl;          }
	public default String getIssueName() {       return this.getProps().issueName;       }
	public default String getIssueURL() {        return this.getProps().issueUrl;        }



}
