package com.rr.rrol.game.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.rr.rrol.game.objects.Spline;
import com.rr.rrol.game.objects.SplineSegment;
import com.rr.rrol.game.objects.Switch;
import com.rr.rrol.game.objects.enums.SplineType;
import com.rr.rrol.game.objects.enums.SwitchState;
import com.rr.rrol.game.objects.enums.SwitchType;
import com.rr.rrol.se.model.Save;
import com.rr.rrol.se.model.property.item.Point3D;

public class MapRenderer {

	private static final int MAP_HEIGHT = 194000;
    private static final int MAP_WIDTH = 203404;
    private static final int OFFSET_X = 0;
    private static final int OFFSET_Y = -4000;
    private double minSize;
    private boolean holding;
    
    private double mapBgrSize = 850.0f;
    private double mapBgrScale = 3;
    
    public BufferedImage getImage(Save save) throws Exception {
    	String mapBgr = "mapBgr.jpg";
    	InputStream in = MapRenderer.class.getResourceAsStream(mapBgr);
    	File file = new File(mapBgr);
    	file.getCanonicalFile();
        BufferedImage i = ImageIO.read(in);
        
        BufferedImage image = new BufferedImage((int)(i.getWidth()*mapBgrScale), (int)(i.getHeight()*mapBgrScale), BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        AffineTransform at = new AffineTransform();
        at.scale(mapBgrScale, mapBgrScale);
        g.setTransform(at);
        
        g.drawImage(i, 0, 0, null);
        
        init(g, save);
        
        return image;
    }
    
    public void writeImageToDisk(BufferedImage image, File file) throws IOException {
    	ImageIO.write(image, "png", file);
    }
    
    private void init(Graphics2D g, Save save) throws Exception {
    	initSplines(g, save);
    	initSwitches(g, save);
//    	initWaterTowers(g, properties);
//    	initFirewood(g, properties);
//    	initVehicles(g, properties);
    }
    
//    private void initVehicles(Graphics2D g, Properties properties) {
//    	List<Vehicle> vehicles = properties.vehicles;
//    	for(Vehicle v : vehicles) {
////    		System.out.println(v.type);
//    		Location l = translate(v.location);
//    		drawCircle(g, l.getX(), l.getY(), 1, Color.GREEN);
//    	}
//    }
//    
//    private void initFirewood(Graphics2D g, Properties properties) {
//    	List<Industry> firewoods = properties.industries.stream().filter(x -> x.type == IndustryType.industry_firewooddepot).collect(toList());
//    	for(Industry w : firewoods) {
//    		Location l = translate(w.location);
//    		drawCircle(g, l.getX(), l.getY(), 1, Color.getY()ELLOW);
//    	}
//    }
//    
//    private void initWaterTowers(Graphics2D g, Properties properties) {
//    	List<WaterTower> waterTowers = properties.watertowers;
//    	for(WaterTower w : waterTowers) {
//    		Location l = translate(w.location);
//    		drawCircle(g, l.getX(), l.getY(), 1, Color.CYAN);
//    	}
//    }
//    
    private void initSwitches(Graphics2D g, Save save) throws Exception {
    	g.setStroke(new BasicStroke(1));
    	double len = 3;
    	
    	List<Switch> switches = save.getSwitches();
    	for(Switch s : switches) {
    		Point2D start = translateXY(s.getLocation());
    		double a = Math.toRadians(s.getRotation().getY());
    		double b = Math.toRadians(s.getRotation().getY());
    		if(s.getType().equals(SwitchType.SwitchLeft) || s.getType().equals(SwitchType.SwitchLeftMirror)) {
    			b -= 0.1;
    		}
    		if(s.getType().equals(SwitchType.SwitchRight) || s.getType().equals(SwitchType.SwitchRightMirror)) {
    			b += 0.1;
    		}
    		if(s.getType().equals(SwitchType.SwitchCross90)) {
    			b += Math.toRadians(90);
    			Point2D st = new Point2D.Double(start.getX() + 0.25*Math.sin(a), start.getY() + 0.5*Math.cos(a));
    			Point2D s1 = new Point2D.Double(st.getX() - 0.5*Math.sin(a), st.getY() + 0.5*Math.cos(a));
    			Point2D e1 = new Point2D.Double(st.getX() + 0.5*Math.sin(a), st.getY() - 0.5*Math.cos(a));
    			Point2D s2 = new Point2D.Double(st.getX() - 0.5*Math.sin(b), st.getY() + 0.5*Math.cos(b));
    			Point2D e2 = new Point2D.Double(st.getX() + 0.5*Math.sin(b), st.getY() - 0.5*Math.cos(b));
    			Path2D path = new Path2D.Double();
        		path.moveTo(s1.getX(), s1.getY());
        		path.lineTo(e1.getX(), e1.getY());    		
    			g.draw(path);
    			Path2D path2 = new Path2D.Double();
        		path2.moveTo(s2.getX(), s2.getY());
        		path2.lineTo(e2.getX(), e2.getY());   		
    			g.draw(path2);
    			continue;
    		}
    		Point2D end = new Point2D.Double(start.getX() + len*Math.sin(a), start.getY() - len*Math.cos(a));
    		Point2D fork = new Point2D.Double(start.getX() + len*Math.sin(b), start.getY() - len*Math.cos(b));
    		
    		Color c1 = Color.GRAY.darker();
    		Color c2 = Color.RED;
    		if(s.getState().equals(SwitchState.Side)) {
    			c1 = Color.RED;
    			c2 = Color.GRAY.darker();
    			g.setColor(c1);
        		Path2D path = new Path2D.Double();
        		path.moveTo(start.getX(), start.getY());
        		path.lineTo(end.getX(), end.getY());    		
    			g.draw(path);
        		g.setColor(c2);
    			Path2D path2 = new Path2D.Double();
        		path2.moveTo(start.getX(), start.getY());
        		path2.lineTo(fork.getX(), fork.getY());   		
    			g.draw(path2);
    		} else {
        		g.setColor(c2);
    			Path2D path2 = new Path2D.Double();
        		path2.moveTo(start.getX(), start.getY());
        		path2.lineTo(fork.getX(), fork.getY());   		
    			g.draw(path2);
    			g.setColor(c1);
        		Path2D path = new Path2D.Double();
        		path.moveTo(start.getX(), start.getY());
        		path.lineTo(end.getX(), end.getY());    		
    			g.draw(path);
    		}
    		
    	}
    }
//    
//    private void initIndustries(Graphics2D g, Properties properties) {
//    	List<Industry> firewoods = properties.industries.stream().filter(x -> x.type == IndustryType.industry_firewooddepot).collect(toList());
//    	List<Industry> industries = properties.industries.stream().filter(x -> x.type != IndustryType.industry_firewooddepot).collect(toList());    	
//    }
    
    private void initSplines(Graphics2D g, Save save) throws Exception {
    	List<Spline> allSplines = save.getSplines();
    	List<Spline> rails = allSplines.stream().filter(x -> x.getType() == SplineType.RailNG).collect(Collectors.toList());
    	List<Spline> trestle = allSplines.stream().filter(x -> x.getType() == SplineType.TrestleDeck).collect(Collectors.toList());
    	List<Spline> groundwork = allSplines.stream().filter(x -> x.getType() == SplineType.Grade || x.getType() == SplineType.GradeConstant).collect(Collectors.toList());
    	List<Spline> stone = allSplines.stream().filter(x -> x.getType() == SplineType.StonewallVariable || x.getType() == SplineType.StonewallConstant).collect(Collectors.toList());
    	List<Spline> bridge = allSplines.stream().filter(x -> x.getType() == SplineType.Trestle).collect(Collectors.toList());
    	List<Spline> bridgeSteel = allSplines.stream().filter(x -> x.getType() == SplineType.SplineTrestleSteel).collect(Collectors.toList());
    	
    	drawSpline(g, groundwork, 3, Color.ORANGE.darker());
    	drawSpline(g, stone, 3, Color.GRAY);
    	drawSpline(g, bridge, 3, Color.RED.darker());
    	drawSpline(g, bridgeSteel, 3, Color.BLUE.darker());
    	drawSpline(g, rails, 1, Color.BLACK);
    	drawSpline(g, trestle, 1, Color.YELLOW.darker());
    }
    
    public void drawSpline(Graphics2D g, List<Spline> spline, float width, Color color) {
    	g.setStroke(new BasicStroke(width));
    	g.setColor(color);
    	for(Spline s : spline) {
    		for(SplineSegment ss : s.getSegments()) {
    			if(ss.isVisible()) {
	    			Path2D path = new Path2D.Double();
	    			Point2D start = translateXY(ss.getStart());
	    			Point2D end = translateXY(ss.getEnd());
	    			path.moveTo(start.getX(), start.getY());
	    			path.lineTo(end.getX(), end.getY());
	    			g.draw(path);
    			}
    		}
    	}
    }
    
//    public void drawCircle(Graphics2D g, double x, double y, double size, Color color) {
//    	g.setColor(color);
//    	Shape shape = new Ellipse2D.Double(x - size, y - size, 2.0 * size, 2.0 * size);
//    	g.fill(shape);
//    }
    
    private Point2D translateXY(Point3D location) {
    	return new Point2D.Double((mapBgrSize)*(-location.getX()+MAP_WIDTH+OFFSET_X)/(2*MAP_WIDTH), (mapBgrSize)*(-location.getY()+MAP_HEIGHT+OFFSET_Y)/(2*MAP_HEIGHT));
    }
}
