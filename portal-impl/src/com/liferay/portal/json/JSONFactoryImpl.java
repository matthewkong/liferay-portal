/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.json.JSONTransformer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.jabsorb.serializer.MarshallException;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONFactoryImpl implements JSONFactory {

	public JSONFactoryImpl() {
		JSONInit.init();

		_serializer = new org.jabsorb.JSONSerializer();

		 try {
			 _serializer.registerDefaultSerializers();
		 }
		 catch (Exception e) {
			 _log.error(e, e);
		 }
	}

	public JSONArray createJSONArray() {
		return new JSONArrayImpl();
	}

	public JSONArray createJSONArray(String json) throws JSONException {
		return new JSONArrayImpl(json);
	}

	public <T> JSONDeserializer<T> createJSONDeserializer() {
		return new JSONDeserializerImpl<T>();
	}

	public JSONObject createJSONObject() {
		return new JSONObjectImpl();
	}

	public JSONObject createJSONObject(String json) throws JSONException {
		return new JSONObjectImpl(json);
	}

	public JSONSerializer createJSONSerializer() {
		return new JSONSerializerImpl();
	}

	public Object deserialize(JSONObject jsonObj) {
		return deserialize(jsonObj.toString());
	}

	public Object deserialize(String json) {
		try {
			return _serializer.fromJSON(json);
		}
		catch (Exception e) {
			 _log.error(e, e);

			throw new IllegalStateException("Unable to deserialize object", e);
		}
	}

	public Object looseDeserialize(String json) {
		try {
			return createJSONDeserializer().deserialize(json);
		}
		catch (Exception e) {
			 _log.error(e, e);

			throw new IllegalStateException("Unable to deserialize object", e);
		}
	}

	public <T> T looseDeserialize(String json, Class<T> clazz) {
		return (T) createJSONDeserializer().use(null, clazz).deserialize(json);
	}

	public String looseSerialize(Object object) {
		return createJSONSerializer().serialize(object);
	}

	public String looseSerialize(Object object, String... includes) {
		return createJSONSerializer().include(includes).serialize(object);
	}

	public String looseSerialize(
		Object object, JSONTransformer jsonTransformer, Class<?> clazz) {

		return createJSONSerializer().
			transform(jsonTransformer, clazz).
			serialize(object);
	}

	public String looseSerializeDeep(Object object) {
		return createJSONSerializer().serializeDeep(object);
	}

	public String serialize(Object object) {
		try {
			return _serializer.toJSON(object);
		}
		catch (MarshallException me) {
			_log.error(me, me);

			throw new IllegalStateException("Unable to serialize oject", me);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JSONFactoryImpl.class);

	private org.jabsorb.JSONSerializer _serializer;

}