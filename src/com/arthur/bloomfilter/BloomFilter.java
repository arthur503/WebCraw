package com.arthur.bloomfilter;

import java.util.BitSet;

public class BloomFilter {
	
	public static void main(String[] argv){
		
		BloomFilter bf = new BloomFilter(10000);
		String s1 = "www.baidu.com";
		String s2 = "www.google.com";
		bf.add(s1);
		for(int i=0;i<1000000;i++){
			
		}
		
		System.out.println(bf.check(s1));
		System.out.println(bf.check(s2));
	}
	
	private BitSet bitSet;
	private int HASH_FUNC_COUNT = 8;	//最多有11个哈希函数。
	private int bitSetCount;
	
	/**
	 * 布隆过滤器。
	 * 采用bitSetCount位BitSet存储，默认使用8个hash函数定位。
	 */
	public BloomFilter(int bitSetCount){
		this.bitSetCount =  bitSetCount;
		bitSet = new BitSet(bitSetCount);
	}
	
	public boolean checkAndAdd(String url){
		int[] positions = getHashPositions(url);
		if(check(positions)){
//			System.out.println("False!String ["+url+"] have been added before!");
			return false;
		}else{
			add(positions);
//			System.out.println("TRUE! Add String ["+url+"] Done!");
			return true;
		}
	}
	
	public void add(String url){
		int[] positions = getHashPositions(url);
		add(positions);
	}
	
	private void add(int[] positions) {
		// TODO Auto-generated method stub
		for(int i=0;i<positions.length;i++){
			bitSet.set(positions[i]);
		}
	}

	public boolean check(String url){
		int[] positions = getHashPositions(url);
		return check(positions);

	}

	private boolean check(int[] positions) {
		// TODO Auto-generated method stub
		for(int i=0;i<positions.length;i++){
			if(!bitSet.get(positions[i])){
				return false;
			}
		}
		return true;
	}

	private int[] getHashPositions(String url) {
		// TODO Auto-generated method stub
		int[] positions = new int[HASH_FUNC_COUNT];
		for(int i=0;i<HASH_FUNC_COUNT;i++){
			positions[i] = (int) (Math.abs(getHashCode(url, i)) % bitSetCount);
		}
		return positions;
	}

	private long getHashCode(String url, int i) {
		// TODO Auto-generated method stub
		if(i<0 || i>10){
			System.out.println("Error in getHashCode! Out of range!");
			return -1;
		}
		GeneralHashFunctionLibrary hashLib = new GeneralHashFunctionLibrary();
		switch(i){
		case 0:
			return hashLib.APHash(url);
		case 1:
			return hashLib.BKDRHash(url);
		case 2:
			return hashLib.BPHash(url);
		case 3:
			return hashLib.DEKHash(url);
		case 4:
			return hashLib.DJBHash(url);
		case 5:
			return hashLib.ELFHash(url);
		case 6:
			return hashLib.FNVHash(url);
		case 7:
			return hashLib.JSHash(url);
		case 8:
			return hashLib.PJWHash(url);
		case 9:
			return hashLib.RSHash(url);
		case 10:
			return hashLib.SDBMHash(url);
		}
		return -1;
	}

}
