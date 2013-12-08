package test;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericTest<T>
{
    public List<String> objs1;
	public int i;
	public List<List<String>> objs3;
	public T objs4;
	public T objs5;

	public void Gtest() throws Exception
	{
		//タイプ型
		Type type;

		//フィールド型にStringListのフィールドを取得
		Field field1 = GenericTest.class.getField("objs1");
		//出力
		//タイプ型にフィールドのタイプを入れる
		type = field1.getGenericType();
		//タイプを出力
		System.out.println("objs1のタイプは" + type + " :  リストのジェネリック型は" + type.getClass());

		ParameterizedType paramType = (ParameterizedType)field1.getGenericType();
		for(Type i : paramType.getActualTypeArguments()) {
			System.out.println("引数の型？" + i + " : " + i.getClass());
		}

		Field field2 = GenericTest.class.getField("i");
		System.out.println("[i]");
		type = field2.getGenericType();
		System.out.println(type + " : " + type.getClass());


		Field field3 = GenericTest.class.getField("objs3");
		System.out.println("[objs3]");
		type = field3.getGenericType();
		System.out.println(type + " : " + type.getClass());

		type = ( (ParameterizedType)type).getActualTypeArguments()[0];
		System.out.println(type + " : " + type.getClass());
		type = ( (ParameterizedType)type).getActualTypeArguments()[0];
		System.out.println(type + " : " + type.getClass());


		Field field4 = GenericTest.class.getField("objs4");
		System.out.println("[objs4]");
		type = field4.getGenericType();
		System.out.println(type + " : " + type.getClass());

		Field field5 = GenericTest.class.getField("objs5");
		System.out.println("[objs5]");
		type = field5.getGenericType();
		System.out.println(type + " : " + type.getClass());

		type = ( (GenericArrayType)type).getGenericComponentType();
		System.out.println(type + " : " + type.getClass());
	}
}