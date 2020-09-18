/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Area;
import exception.AreaNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Owen Tay
 */
@Local
public interface AreaSessionLocal {

    public Area createArea(Area area);

    public Area createArea(String areaName);
    
    public List<Area> retrieveAllAreas();

    public Area retrieveAreaById(Long areaId) throws AreaNotFoundException;

    public List<Area> retrieveAreasByName(String areaName) throws AreaNotFoundException;

    public void updateArea(Area area);

    public void updateArea(Long areaId, String areaName) throws AreaNotFoundException;

    public void deleteArea(Long areaId) throws AreaNotFoundException;
}
