/**
 * Copyright (C) 2013
 * @author like
 */
package org.likefly.owl;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/*
 * OWL对象Id生成器，有随机数产生
 */
public class ObjectIdGenerator {
	
	private Random rand = new Random(47);
	private Set<Integer> IDS = new HashSet<Integer>();
	
	private String getUnusedNumber() {
		int s = rand.nextInt(10000); //ID生成规则是名字首字母+一个思维随机数
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
