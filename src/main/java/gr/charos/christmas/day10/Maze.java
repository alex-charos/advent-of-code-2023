package gr.charos.christmas.day10;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Maze {
	
	/*
	 * (0,0)(1,0)(2,0)(3,0)(4,0)... 
	 * (0,1)(1,1)(2,1)(3,1)(4,1)...
	 * (0,2)(1,2)(2,2)(3,2)(4,2)...
	 */
	public final List<Position> positions;
	private final Position animalPosition;
	public Set<Position> onLoopsRight = new HashSet<Position>();
	public Set<Position> onLoopsLeft = new HashSet<Position>();
	private final int maxX, maxY;

	public Maze(List<Position> positions, int maxX, int maxY) {
		this.positions = positions;
		this.animalPosition = this.positions.stream().filter(p -> p.tile.equals(Tile.STARTING_POSITION)).findFirst()
				.get();
		this.animalPosition.stepsFromStartingPosition=0;
		this.animalPosition.setInMainLoop(true);
		this.maxX = maxX;
		this.maxY=maxY;
	}

	
	public boolean isEdge(Position p) {
		return p.point.x ==0 || p.point.y ==0 || p.point.x==this.maxX || p.point.y==this.maxY;
	}

	
	
	public void calculateMainLoop() {
		List<Position> toFollow = followPipe(animalPosition);
	
		for (Position p : toFollow) {
			int steps = 0;
			
			Direction d = null;
			if (p.point.x > animalPosition.point.x) {
				d = Direction.EAST;
			}
			if (p.point.x < animalPosition.point.x) {
				d = Direction.WEST;
			}
			if (p.point.y >animalPosition.point.y) {
				d = Direction.SOUTH;
			}
			if (p.point.y < animalPosition.point.y) {
				d = Direction.NORTH;
			}
			PointAndDirection pd = new PointAndDirection(p.point, d);
			while (p!=animalPosition) {
				steps++;
				p.setStepsIfLess(steps);
				p.setInMainLoop(true);
				
				pd = p.tile.apply.apply(pd);
				p = getPositionInPoint(pd.point).get();
				  
			}
		}
		
		onLoopsRight = new HashSet<Maze.Position>();
		onLoopsLeft = new HashSet<Maze.Position>();
		Position p = null;
		for (Position options : toFollow) {
			Direction d = null;
			if (options.point.x > animalPosition.point.x) {
				d = Direction.EAST;
			}
			if (options.point.x < animalPosition.point.x) {
				d = Direction.WEST;
			}
			if (options.point.y >animalPosition.point.y) {
				d = Direction.SOUTH;
			}
			if (options.point.y < animalPosition.point.y) {
				d = Direction.NORTH;
			}
			if (Direction.EAST.equals(d) || Direction.NORTH.equals(d) ) {
				p = options;
			}
		}
		
		p = toFollow.get(0);
		
			Direction d = null;
			if (p.point.x > animalPosition.point.x) {
				d = Direction.EAST;
			}
			if (p.point.x < animalPosition.point.x) {
				d = Direction.WEST;
			}
			if (p.point.y >animalPosition.point.y) {
				d = Direction.SOUTH;
			}
			if (p.point.y < animalPosition.point.y) {
				d = Direction.NORTH;
			}
			PointAndDirection pd = new PointAndDirection(p.point, d);
			while (p!=animalPosition) {
				d = pd.direction;
				if (Direction.EAST.equals(d)) {
					Optional<Position> nonMainRight = this.getPositionInPoint(new Point(p.point.x, p.point.y+1)).filter(zp->!zp.isInMainLoop());
					if (nonMainRight.isPresent()) {
						onLoopsRight.add(nonMainRight.get());
					}
					Optional<Position> nonMainLeft = this.getPositionInPoint(new Point(p.point.x, p.point.y-1)).filter(zp->!zp.isInMainLoop());
					if (nonMainLeft.isPresent()) {
						onLoopsLeft.add(nonMainLeft.get());
					}
					
				}
				if (Direction.WEST.equals(d)) {
					Optional<Position> nonMain = this.getPositionInPoint(new Point(p.point.x, p.point.y-1)).filter(zp->!zp.isInMainLoop());
					if (nonMain.isPresent()) {
						onLoopsRight.add(nonMain.get());
					}
					Optional<Position> nonMainLeft = this.getPositionInPoint(new Point(p.point.x, p.point.y+1)).filter(zp->!zp.isInMainLoop());
					if (nonMainLeft.isPresent()) {
						onLoopsLeft.add(nonMainLeft.get());
					}
				}
				
				if (Direction.SOUTH.equals(d)) {
					Optional<Position> nonMain = this.getPositionInPoint(new Point(p.point.x-1, p.point.y)).filter(zp->!zp.isInMainLoop());
					if (nonMain.isPresent()) {
						onLoopsRight.add(nonMain.get());
					}
					Optional<Position> nonMainLeft = this.getPositionInPoint(new Point(p.point.x+1, p.point.y)).filter(zp->!zp.isInMainLoop());
					if (nonMainLeft.isPresent()) {
						onLoopsLeft.add(nonMainLeft.get());
					}
				}
				if (Direction.NORTH.equals(d)) {
					Optional<Position> nonMain = this.getPositionInPoint(new Point(p.point.x+1, p.point.y)).filter(zp->!zp.isInMainLoop());
					if (nonMain.isPresent()) {
						onLoopsRight.add(nonMain.get());
					}
					Optional<Position> nonMainLeft = this.getPositionInPoint(new Point(p.point.x-1, p.point.y)).filter(zp->!zp.isInMainLoop());
					if (nonMainLeft.isPresent()) {
						onLoopsLeft.add(nonMainLeft.get());
					}
				}
				
				pd = p.tile.apply.apply(pd);
				
				//direction has changed? regather! 
				if (!pd.direction.equals(d)) {
					d = pd.direction;
					if (Direction.EAST.equals(d)) {
						Optional<Position> nonMainRight = this.getPositionInPoint(new Point(p.point.x, p.point.y+1)).filter(zp->!zp.isInMainLoop());
						if (nonMainRight.isPresent()) {
							onLoopsRight.add(nonMainRight.get());
						}
						Optional<Position> nonMainLeft = this.getPositionInPoint(new Point(p.point.x, p.point.y-1)).filter(zp->!zp.isInMainLoop());
						if (nonMainLeft.isPresent()) {
							onLoopsLeft.add(nonMainLeft.get());
						}
						
					}
					if (Direction.WEST.equals(d)) {
						Optional<Position> nonMain = this.getPositionInPoint(new Point(p.point.x, p.point.y-1)).filter(zp->!zp.isInMainLoop());
						if (nonMain.isPresent()) {
							onLoopsRight.add(nonMain.get());
						}
						Optional<Position> nonMainLeft = this.getPositionInPoint(new Point(p.point.x, p.point.y+1)).filter(zp->!zp.isInMainLoop());
						if (nonMainLeft.isPresent()) {
							onLoopsLeft.add(nonMainLeft.get());
						}
					}
					
					if (Direction.SOUTH.equals(d)) {
						Optional<Position> nonMain = this.getPositionInPoint(new Point(p.point.x-1, p.point.y)).filter(zp->!zp.isInMainLoop());
						if (nonMain.isPresent()) {
							onLoopsRight.add(nonMain.get());
						}
						Optional<Position> nonMainLeft = this.getPositionInPoint(new Point(p.point.x+1, p.point.y)).filter(zp->!zp.isInMainLoop());
						if (nonMainLeft.isPresent()) {
							onLoopsLeft.add(nonMainLeft.get());
						}
					}
					if (Direction.NORTH.equals(d)) {
						Optional<Position> nonMain = this.getPositionInPoint(new Point(p.point.x+1, p.point.y)).filter(zp->!zp.isInMainLoop());
						if (nonMain.isPresent()) {
							onLoopsRight.add(nonMain.get());
						}
						Optional<Position> nonMainLeft = this.getPositionInPoint(new Point(p.point.x-1, p.point.y)).filter(zp->!zp.isInMainLoop());
						if (nonMainLeft.isPresent()) {
							onLoopsLeft.add(nonMainLeft.get());
						}
					}
				}
				
				
				
				
				p = getPositionInPoint(pd.point).get();
				  
			}
		
			//remove those at edge or neighboring at edge
			//they may not be on the right
			List<Position> nonAccountedFor = this.positions.stream()
					.filter(item-> {
						return !item.isInMainLoop() && !this.onLoopsLeft.contains(item)
								&& !this.onLoopsRight.contains(item);
			}).distinct().collect(Collectors.toList());
			
			
			
			int count = 0;
			List<Position> checkingLeft = new ArrayList<Maze.Position>(nonAccountedFor);
			List<Position> checkingRight = new ArrayList<Maze.Position>(nonAccountedFor);
			while (count <checkingLeft.size()) {
				Position each = checkingLeft.get(count);
				boolean isNeighbourLeft = onLoopsLeft.stream().anyMatch(item ->item.isNeighbourTo(each));
				if (isNeighbourLeft) {
					onLoopsLeft.add(each);
					checkingLeft.remove(count);
					count = 0;
				} else {
					count++;
				}
			}
			count = 0;
			while (count <checkingRight.size()) {
				Position each = checkingRight.get(count);
				boolean isNeighbourRight = onLoopsRight.stream().anyMatch(item ->item.isNeighbourTo(each));
				if (isNeighbourRight) {
					onLoopsRight.add(each);
					checkingRight.remove(count);
					count = 0;
				} else {
					count++;
				}
			}
			
			
			for (Position each : this.positions) {
				Set<Position> neighboursToRight = new HashSet<Maze.Position>();
				for (Position left : this.onLoopsRight) {
					if (each.isNeighbourTo(left) && !each.isInMainLoop()) {
						neighboursToRight.add(each);
					}
				}
				
				Set<Position> neighboursToLeft = new HashSet<Maze.Position>();
				for (Position left : this.onLoopsLeft) {
					if (each.isNeighbourTo(left) && !each.isInMainLoop()) {
						neighboursToLeft.add(each);
					}
				}
			}
			
			

	}
	
	public void addToSides() {
		Set<Position> newLeft = new HashSet<Maze.Position>(this.onLoopsLeft);
		
		for (Position p : newLeft) {
			Optional<Position> ne1 = getPositionInPoint(new Point(p.point.x+1, p.point.y));
			Optional<Position> ne2 = getPositionInPoint(new Point(p.point.x, p.point.y+1));
			Optional<Position> ne3 = getPositionInPoint(new Point(p.point.x-1, p.point.y));
			Optional<Position> ne4 = getPositionInPoint(new Point(p.point.x, p.point.y-1));
			
			if (ne1.isPresent() && !ne1.get().inMainLoop) {
				this.onLoopsLeft.add(ne1.get());
			}
			
			if (ne2.isPresent() && !ne2.get().inMainLoop) {
				this.onLoopsLeft.add(ne2.get());
			}
			
			if (ne3.isPresent() && !ne3.get().inMainLoop) {
				this.onLoopsLeft.add(ne3.get());
			}
			
			if (ne4.isPresent() && !ne4.get().inMainLoop) {
				this.onLoopsLeft.add(ne4.get());
			}
			
		}
		
		
	}
	
	private Optional<Position> getPositionInPoint(Point pa) {
		return positions.stream().filter(p -> p.point.equals(pa)).findFirst();
	}
	
	private List<Position> followPipe(Position current){
		List<Position> toFollow = new ArrayList<Maze.Position>();
		
		
		// look above it
		final Point pointAbove = new Point(current.point.x, current.point.y - 1);
		Optional<Position> positionAbove = getPositionInPoint(pointAbove);

		if (positionAbove.isPresent()) {
			// above it makes sense to have VERTICAL_PIPE, SOUTH_WEST_BEND or
			// SOUTH_EAST_BEND
			if (Tile.VERTICAL_PIPE.equals(positionAbove.get().tile) || Tile.SOUTH_WEST_BEND.equals(positionAbove.get().tile)
					|| Tile.SOUTH_EAST_BEND.equals(positionAbove.get().tile)) {
				toFollow.add(positionAbove.get());
			}
		}

		// look below it
		final Point pointBelow = new Point(current.point.x, current.point.y + 1);
		Optional<Position> positionBelow = getPositionInPoint(pointBelow);

		if (positionBelow.isPresent()) {
			// below it makes sense to have VERTICAL_PIPE, NORTH_EAST or
			// NORTH_WEST_BEND
			if (Tile.VERTICAL_PIPE.equals(positionBelow.get().tile) || Tile.NORTH_EAST_BEND.equals(positionBelow.get().tile)
					|| Tile.NORTH_WEST_BEND.equals(positionBelow.get().tile)) {
				toFollow.add(positionBelow.get());
			}
		}
		
		
		// look to the right of  it
		final Point pointRight = new Point(current.point.x+1, current.point.y );
		Optional<Position> positionRight = getPositionInPoint(pointRight);

		if (positionRight.isPresent()) {
			// below it makes sense to have HORIZONTAL_PIPE, SOUTH_WEST_BEND or
			// NORTH_WEST_BEND
			if (Tile.HORIZONTAL_PIPE.equals(positionRight.get().tile) || Tile.SOUTH_WEST_BEND.equals(positionRight.get().tile)
					|| Tile.NORTH_WEST_BEND.equals(positionRight.get().tile)) {
				toFollow.add(positionRight.get());
			}
		}
		
		// look to the LEFT of  it
		final Point pointLeft = new Point(current.point.x-1, current.point.y );
		Optional<Position> positionLeft =  getPositionInPoint(pointLeft);

		if (positionLeft.isPresent()) {
			// below it makes sense to have HORIZONTAL_PIPE, NORTH_EAST_END or
			// SOUTH_EAST_BEND
			if (Tile.HORIZONTAL_PIPE.equals(positionLeft.get().tile) || Tile.NORTH_EAST_BEND.equals(positionLeft.get().tile)
					|| Tile.SOUTH_EAST_BEND.equals(positionLeft.get().tile)) {
				toFollow.add(positionLeft.get());
			}
		}
		
		return toFollow;
	}

	public static class PointAndDirection {
		public Point point;
		public Direction direction;
		public PointAndDirection(Point point, Direction direction) {
			super();
			this.point = point;
			this.direction = direction;
		}
		@Override
		public String toString() {
			return "PointAndDirection [point=" + point + ", direction=" + direction + "]";
		}
		
		
	}
	
	public static enum SideOfLoop {
		LEFT, RIGHT;
	}
	public static enum Direction {
		EAST,WEST,NORTH,SOUTH;
	}
	
	public static enum Tile {
		VERTICAL_PIPE("|", (p) -> {
			if (p.direction.equals(Direction.SOUTH)) {
				return new PointAndDirection( new Point(p.point.x, p.point.y+1), Direction.SOUTH);
			}
			if (p.direction.equals(Direction.NORTH)) {
				return new PointAndDirection( new Point(p.point.x, p.point.y-1), Direction.NORTH);
			}
			return null;
		}), 
		
		HORIZONTAL_PIPE("-", (p) -> {
			if (p.direction.equals(Direction.EAST)) {
				return new PointAndDirection( new Point(p.point.x+1, p.point.y), Direction.EAST);
			}
			if (p.direction.equals(Direction.WEST)) {
				return new PointAndDirection( new Point(p.point.x-1, p.point.y), Direction.WEST);
			}
			return null;
		}), 
		NORTH_EAST_BEND("L", (p) -> {
			if (p.direction.equals(Direction.SOUTH)) {
				return new PointAndDirection( new Point(p.point.x+1, p.point.y), Direction.EAST);
			}
			if (p.direction.equals(Direction.WEST)) {
				return new PointAndDirection( new Point(p.point.x, p.point.y-1), Direction.NORTH);
			}
			return null;
		}), 
		NORTH_WEST_BEND("J", (p) -> {
			if (p.direction.equals(Direction.SOUTH)) {
				return new PointAndDirection( new Point(p.point.x-1, p.point.y), Direction.WEST);
			}
			if (p.direction.equals(Direction.EAST)) {
				return new PointAndDirection( new Point(p.point.x, p.point.y-1), Direction.NORTH);
			}
			return null;
		}), 
		SOUTH_WEST_BEND("7", (p) -> {
			if (p.direction.equals(Direction.EAST)) {
				return new PointAndDirection( new Point(p.point.x, p.point.y+1), Direction.SOUTH);
			}
			if (p.direction.equals(Direction.NORTH)) {
				return new PointAndDirection( new Point(p.point.x-1, p.point.y), Direction.WEST);
			}
			return null;
		}),
		SOUTH_EAST_BEND("F", (p) -> {
			if (p.direction.equals(Direction.WEST)) {
				return new PointAndDirection( new Point(p.point.x, p.point.y+1), Direction.SOUTH);
			}
			if (p.direction.equals(Direction.NORTH)) {
				return new PointAndDirection( new Point(p.point.x+1, p.point.y), Direction.EAST);
			}
			return null;
		}), 
		GROUND(".", (p) -> {

			return null;
		}), 
		STARTING_POSITION("S", (p) -> {

			return null;
		});

		String sign;
		Function<PointAndDirection, PointAndDirection> apply;
		boolean travelsNorth;
		boolean travelsSouth;
		boolean travelsEast;
		boolean travelsWest;

		Tile(	String sign, 
				Function<PointAndDirection, PointAndDirection> apply
//				,
//				boolean travelsNorth,
//				boolean travelsSouth,
//				boolean travelsEast,
//				boolean travelsWest
				) {
			this.sign = sign;
			this.apply = apply;
//			this.travelsNorth= travelsNorth;
//			this.travelsSouth = travelsSouth;
//			this.travelsEast = travelsEast;
//			this.travelsWest = travelsWest;
		}

		public static Tile of(String s) {
			return Arrays.asList(Tile.values()).stream().filter(p -> s.equals(p.sign)).findFirst().get();
		}

	}

	public static class Position implements Comparable<Position> {
		
		private final Point point;
		private final Tile tile;

		private int stepsFromStartingPosition = -1;
		
		private boolean inMainLoop;
		private boolean enclosed;

		public Position(Point point, Tile tile) {
			this.point = point;
			this.tile = tile;
		}

		public boolean isNeighbourTo(Position p) {
			if (this.point.x == p.point.x && ( this.point.y == p.point.y -1 || this.point.y == p.point.y+1)) {
				return true;
			}
			if (this.point.y == p.point.y && ( this.point.x == p.point.x -1 || this.point.x == p.point.x+1)) {
				return true;
			}
			return false;
		}
		
		public void setStepsIfLess(int distance) {
			if (stepsFromStartingPosition == -1 || distance < stepsFromStartingPosition) {
				this.stepsFromStartingPosition = distance;
			}
		}
		
		

		public int getStepsFromStartingPosition() {
			return stepsFromStartingPosition;
		}

		public static Position of(int x, int y, String sign) {
			return new Position(new Point(x, y), Tile.of(sign));
		}

		@Override
		public int compareTo(Position o) {
			if (this.stepsFromStartingPosition > o.stepsFromStartingPosition) {
				return -1;
			} else if (this.stepsFromStartingPosition < o.stepsFromStartingPosition) {
				return 1;
			}
			return 0;
		}

		public boolean isInMainLoop() {
			return inMainLoop;
		}

		public void setInMainLoop(boolean inMainLoop) {
			this.inMainLoop = inMainLoop;
		}

		public boolean isEnclosed() {
			return enclosed;
		}

		public void setEnclosed(boolean enclosed) {
			this.enclosed = enclosed;
		}

		@Override
		public String toString() {
			return "Position [point=" + point + ", tile=" + tile + ", stepsFromStartingPosition="
					+ stepsFromStartingPosition + ", inMainLoop=" + inMainLoop + ", enclosed=" + enclosed + "]";
		}



	}
}
