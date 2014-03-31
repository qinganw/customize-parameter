/*
+ * The MIT License
 * 
 * Copyright (c) 2004-2010, Sun Microsystems, Inc., Kohsuke Kawaguchi,
 * Jean-Baptiste Quenot, Seiji Sogabe, Tom Huybrechts
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.alven.jenkins;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.model.Job;
import hudson.model.ParameterDefinition;
import hudson.model.ParametersDefinitionProperty;

import java.util.List;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.export.ExportedBean;

/**
 * Keeps a list of the parameters defined for a project.
 * 
 * <p>
 * This class also implements {@link Action} so that <tt>index.jelly</tt>
 * provides a form to enter build parameters.
 */
@ExportedBean(defaultVisibility = 3)
public class CustomizeParametersDefinitionProperty extends
		ParametersDefinitionProperty {
	public CustomizeParametersDefinitionProperty(
			List<ParameterDefinition> parameterDefinitions) {
		super(parameterDefinitions);
	}

	public CustomizeParametersDefinitionProperty(
			ParameterDefinition... parameterDefinitions) {
		super(parameterDefinitions);
	}


	@Extension
	public static class DescriptorImpl extends JobPropertyDescriptor {
		@Override
		public boolean isApplicable(Class<? extends Job> jobType) {
			return AbstractProject.class.isAssignableFrom(jobType);
		}

		@Override
		public JobProperty<?> newInstance(StaplerRequest req,
				JSONObject formData) throws FormException {
			if (formData.isNullObject()) {
				return null;
			}

			JSONObject parameterized = formData.getJSONObject("parameterized");

			if (parameterized.isNullObject()) {
				return null;
			}

			List<ParameterDefinition> parameterDefinitions = Descriptor
					.newInstancesFromHeteroList(req, parameterized,
							"parameter", ParameterDefinition.all());
			if (parameterDefinitions.isEmpty())
				return null;

			return new CustomizeParametersDefinitionProperty(
					parameterDefinitions);
		}

		@Override
		public String getDisplayName() {
			return "CustomizeParametersDefinitionProperty.DisplayName";
		}
	}

	public String getDisplayName() {
		return null;
	}

	public String getIconFileName() {
		return null;
	}

	public String getUrlName() {
		return null;
	}

}
