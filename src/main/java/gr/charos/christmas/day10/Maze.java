package gr.charos.christmas.day10;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Maze {
	

	

	/*
	 * (0,0)(1,0)(2,0)(3,0)(4,0)... 
	 * (0,1)(1,1)(2,1)(3,1)(4,1)...
	 * (0,2)(1,2)(2,2)(3,2)(4,2)...
	 */
	public final List<Position> positions;
	private final Position animalPosition;

	public Maze(List<Position> positions) {
		this.positions = positions;
		this.animalPosition = this.positions.stream().filter(p -> p.tile.equals(Tile.STARTING_POSITION)).findFirst()
				.get();
		this.animalPosition.stepsFromStartingPosition=0;
	}

	public void mapByDistance() {
		List<Position> toFollow = toFollow();
		
		
		for (Position p : toFollow) {
			int steps = 0;
			System.out.println("Starting");
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

				System.out.println("WAS: " + pd);
				System.out.println("steps: " + p.stepsFromStartingPosition);
				System.out.println("applied: " + p.tile);
				pd = p.tile.apply.apply(pd);
				System.out.println("became " + pd);
				p = getPositionInPoint(pd.point).get();
				  
			}
	 
		 
			
			
		}

	}
	
	private Optional<Position> getPositionInPoint(Point pa) {
		return positions.stream().filter(p -> p.point.equals(pa)).findFirst();
	}
	
	private List<Position> toFollow(){
		List<Position> toFollow = new ArrayList<Maze.Position>();
		Position current = animalPosition;
		
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

		Tile(String sign, Function<PointAndDirection, PointAndDirection> apply) {
			this.sign = sign;
			this.apply = apply;
		}

		public static Tile of(String s) {
			return Arrays.asList(Tile.values()).stream().filter(p -> s.equals(p.sign)).findFirst().get();
		}

	}

	public static class Position implements Comparable<Position> {
		
		private final Point point;
		private final Tile tile;

		private int stepsFromStartingPosition = -1;

		public Position(Point point, Tile tile) {
			this.point = point;
			this.tile = tile;
		}

		public void setStepsIfLess(int distance) {
			if (stepsFromStartingPosition == -1 || distance < stepsFromStartingPosition) {
				System.out.println("Updated steps from " + stepsFromStartingPosition + " to " + distance);
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

	}
}
