package ru.seals;

import jakarta.ws.rs.core.Link;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

@SpringBootApplication
public class AuthServiceApplication {

/*
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
*/

	public static void main(String[] args) {
		Integer[] arr = new Integer[]{8,7,7,5,5};
		printMasses(arr);
	}

	public static void printMasses(Integer[] arr) {
		Arrays.sort(arr, Collections.reverseOrder());
		System.out.println(Arrays.toString(arr));
		int[] arr1 = new int[arr.length], arr2 = new int[arr.length];

		int sum1 = 0, sum2 = 0;
		for (int i=0, i1=0, i2=0;  i<arr.length;  ++i) {
			if (sum1 < sum2) {
				arr1[i1++] = arr[i];
				sum1 += arr[i];
			}
			else {
				arr2[i2++] = arr[i];
				sum2 += arr[i];
			}
		}

		System.out.println(Arrays.toString(arr1));
		System.out.println(Arrays.toString(arr2));
	}
}
