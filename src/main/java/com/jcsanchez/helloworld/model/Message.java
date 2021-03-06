/*
 * Copyright 2012-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with
 * the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.jcsanchez.helloworld.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * The message object that provides the message text and its type.
 * </p>
 *
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/lex-models-2017-04-19/Message" target="_top">AWS API
 *      Documentation</a>
 */
public class Message implements Serializable, Cloneable {

    /**
     * <p>
     * The content type of the message string.
     * </p>
     */
    @SerializedName("contentType")
    @Expose
    private String contentType;
    /**
     * <p>
     * The text of the message.
     * </p>
     */
    @SerializedName("content")
    @Expose
    private String content;

    /**
     * <p>
     * The content type of the message string.
     * </p>
     *
     * @param contentType
     *        The content type of the message string.
     * @see ContentType
     */

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * <p>
     * The content type of the message string.
     * </p>
     *
     * @return The content type of the message string.
     * @see ContentType
     */

    public String getContentType() {
        return this.contentType;
    }

    /**
     * <p>
     * The content type of the message string.
     * </p>
     *
     * @param contentType
     *        The content type of the message string.
     * @return Returns a reference to this object so that method calls can be chained together.
     * @see ContentType
     */

    public Message withContentType(String contentType) {
        setContentType(contentType);
        return this;
    }

    /**
     * <p>
     * The content type of the message string.
     * </p>
     *
     * @param contentType
     *        The content type of the message string.
     * @see ContentType
     */

    public void setContentType(ContentType contentType) {
        this.contentType = contentType.toString();
    }

    /**
     * <p>
     * The content type of the message string.
     * </p>
     *
     * @param contentType
     *        The content type of the message string.
     * @return Returns a reference to this object so that method calls can be chained together.
     * @see ContentType
     */

    public Message withContentType(ContentType contentType) {
        setContentType(contentType);
        return this;
    }

    /**
     * <p>
     * The text of the message.
     * </p>
     *
     * @param content
     *        The text of the message.
     */

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * <p>
     * The text of the message.
     * </p>
     *
     * @return The text of the message.
     */

    public String getContent() {
        return this.content;
    }

    /**
     * <p>
     * The text of the message.
     * </p>
     *
     * @param content
     *        The text of the message.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Message withContent(String content) {
        setContent(content);
        return this;
    }

    /**
     * Returns a string representation of this object; useful for testing and debugging.
     *
     * @return A string representation of this object.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getContentType() != null)
            sb.append("ContentType: ").append(getContentType()).append(",");
        if (getContent() != null)
            sb.append("Content: ").append(getContent());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof Message == false)
            return false;
        Message other = (Message) obj;
        if (other.getContentType() == null ^ this.getContentType() == null)
            return false;
        if (other.getContentType() != null && other.getContentType().equals(this.getContentType()) == false)
            return false;
        if (other.getContent() == null ^ this.getContent() == null)
            return false;
        if (other.getContent() != null && other.getContent().equals(this.getContent()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getContentType() == null) ? 0 : getContentType().hashCode());
        hashCode = prime * hashCode + ((getContent() == null) ? 0 : getContent().hashCode());
        return hashCode;
    }

    @Override
    public Message clone() {
        try {
            return (Message) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }
}