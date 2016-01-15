package com.nweaver.grato.util;

public class ChecksumUtils {
	
	/**
	 * Longitudinal redundancy check (LRC) 값을 구한다.
	 * @param data 대상 바이트 배열
	 * @return LRC
	 */
	public static byte calculateLRC(byte[] data) {
		return calculateLRC(data, 0, data.length);
	}
	
	/**
	 * Longitudinal redundancy check (LRC) 값을 구한다.
	 * @param data 대상 바이트 배열
	 * @param start 시작 위치
	 * @param end 종료 위치
	 * @return LRC
	 */
	public static byte calculateLRC(byte[] data, int start, int end) {
		byte checksum = 0;
		for(int i = start; i < end; i++) {
			checksum = (byte) ((checksum + data[i]) & 0xFF);
		}
		checksum = (byte) (((checksum ^ 0xFF) + 1) & 0xFF);
		return checksum;
	}
}
