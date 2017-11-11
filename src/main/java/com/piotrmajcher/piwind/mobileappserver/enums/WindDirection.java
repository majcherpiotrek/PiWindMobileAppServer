package com.piotrmajcher.piwind.mobileappserver.enums;

import java.util.Arrays;
import java.util.List;

public enum WindDirection {
	N, NE, E, SE, S, SW, W, NW;
	
	public static WindDirection oppositeDirection(WindDirection d) {
		switch (d) {
		case N: return S;
		case NE: return SW;
		case E: return W;
		case SE: return NW;
		case S: return N;
		case SW: return NE;
		case W: return E;
		case NW: return SE;
		}
		return null;
	}
	
	public static List<WindDirection> perpendicularDirections(WindDirection d) {
		switch (d) {
		case N: return Arrays.asList(W,E);
		case NE: return Arrays.asList(NW,SE);
		case E: return Arrays.asList(N,S);
		case SE: return Arrays.asList(NE,SW);
		case S: return Arrays.asList(E,W);
		case SW: return Arrays.asList(SE,NW);
		case W: return Arrays.asList(S,N);
		case NW: return Arrays.asList(SW,NE);
		}
		return null;
	}
	
	public static List<WindDirection> crossOppositeDirections(WindDirection d) {
		switch (d) {
		case N: return Arrays.asList(SW,SE);
		case NE: return Arrays.asList (W,S);
		case E: return Arrays.asList (NW,SW);
		case SE: return Arrays.asList(N,W);
		case S: return Arrays.asList(NW, NE);
		case SW: return Arrays.asList(N,E);
		case W: return Arrays.asList(NE,SE);
		case NW: return Arrays.asList(E,S);
		}
		return null;
	}
	
	public static List<WindDirection> crossSimilarDirections(WindDirection d) {
		switch (d) {
		case N: return Arrays.asList(NW,NE);
		case NE: return Arrays.asList (N,E);
		case E: return Arrays.asList (NE,SE);
		case SE: return Arrays.asList(S,E);
		case S: return Arrays.asList(SE,SW);
		case SW: return Arrays.asList(S,W);
		case W: return Arrays.asList(SW,NW);
		case NW: return Arrays.asList(W,N);
		}
		return null;
	}
}
