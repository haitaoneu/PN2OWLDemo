/**
 * Copyright (C) 2013
 * @author like
 */
package org.likefly.owl;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/*
 * OWL����Id�������������������
 */
public class ObjectIdGenerator {
	
	private Random rand = new Random(47);
	private Set<Integer> IDS = new HashSet<Integer>();
	
	private String getUnusedNumber() {
		int s = rand.nextInt(10000); //ID���ɹ�������������ĸ+һ��˼ά�����
		while (IDS.contains(s)) {
			s = rand.nextInt(10000);
		}
		IDS.add(s);
		String xs = String.valueOf(s); 
		String [] ss = {"0000", "000","00","0", ""};
		xs = ss[xs.length()] + xs;
		return xs;
	}
	
	public String generatorOfId(String name) {
		return name.charAt(0) + getUnusedNumber();
	}
	
}
