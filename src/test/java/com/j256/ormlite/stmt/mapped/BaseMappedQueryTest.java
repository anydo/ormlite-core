package com.j256.ormlite.stmt.mapped;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.BaseCoreStmtTest;
import com.j256.ormlite.support.DatabaseResults;

public class BaseMappedQueryTest extends BaseCoreStmtTest {

	@Test
	public void testMappedQuery() throws Exception {
		List<FieldType> argFieldTypeList = new ArrayList<FieldType>();
		List<FieldType> resultFieldTypeList = new ArrayList<FieldType>();
		Field field = Foo.class.getDeclaredField(Foo.ID_COLUMN_NAME);
		String tableName = "basefoo";
		resultFieldTypeList.add(FieldType.createFieldType(connectionSource, tableName, field, Foo.class, 0));
		BaseMappedQuery<Foo, String> baseMappedQuery =
				new BaseMappedQuery<Foo, String>(baseFooTableInfo, "select * from " + tableName, argFieldTypeList,
						resultFieldTypeList) {
				};
		DatabaseResults results = createMock(DatabaseResults.class);
		int colN = 1;
		expect(results.findColumn(Foo.ID_COLUMN_NAME)).andReturn(colN);
		String idString = "deopdjed";
		expect(results.getString(colN)).andReturn(idString);
		expect(results.wasNull(colN)).andReturn(false);
		replay(results);
		Foo baseFoo = baseMappedQuery.mapRow(results);
		assertNotNull(baseFoo);
		assertEquals(idString, baseFoo.id);
		verify(results);
	}
}
