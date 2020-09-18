/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Area;
import exception.AreaNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Owen Tay
 */
@Stateless
public class AreaSession implements AreaSessionLocal {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;

    @Override
    public Area createArea(Area area) {
        em.persist(area);
        em.flush();
        return area;
    }

    @Override
    public Area createArea(String areaName) {
        Area newArea = new Area(areaName);
        return createArea(newArea);
    }

    @Override
    public List<Area> retrieveAllAreas() {
        Query query = em.createQuery("SELECT a FROM Area a");
        return query.getResultList();
    }

    @Override
    public Area retrieveAreaById(Long areaId) throws AreaNotFoundException {
        Area area = em.find(Area.class, areaId);
        if (area != null) {
            return area;
        } else {
            throw new AreaNotFoundException("AreaID " + areaId + " does not exists.");
        }
    }

    @Override
    public List<Area> retrieveAreasByName(String areaName) throws AreaNotFoundException {
        Query query = em.createQuery("SELECT a FROM Area a WHERE a.areaName = :inputAreaName");
        query.setParameter("inputAreaName", areaName);
        List<Area> results = query.getResultList();
        if (!results.isEmpty()) { // if empty then return message in REST
            return results;
        } else {
            throw new AreaNotFoundException("No Areas by the name " + areaName + " was found.");
        }
    }

    @Override
    public void updateArea(Area area) {
        em.merge(area);
    }

    @Override
    public void updateArea(Long areaId, String areaName) throws AreaNotFoundException {
        Area area = retrieveAreaById(areaId);
        area.setAreaName(areaName);
        updateArea(area);
    }

    @Override
    public void deleteArea(Long areaId) throws AreaNotFoundException {
        Area area = retrieveAreaById(areaId);
        em.remove(area);
    }

}
