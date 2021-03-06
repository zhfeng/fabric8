/*
 *  Copyright 2005-2017 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.api.commands;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class GitVersions {

    @JsonProperty
    List<GitVersion> versions = new LinkedList<>();

    @JsonProperty
    String gitMasterUrl = "";

    public GitVersions() {
    }

    public GitVersions(List<GitVersion> versions) {
        this.versions = versions;
    }

    public List<GitVersion> getVersions() {
        return versions;
    }

    public String getGitMasterUrl() {
        return gitMasterUrl;
    }

    public void setGitMasterUrl(String gitMasterUrl) {
        this.gitMasterUrl = gitMasterUrl;
    }

}
