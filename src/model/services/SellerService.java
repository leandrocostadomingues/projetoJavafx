package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	
	
	 private SellerDao  dao = DaoFactory.createSellerDao();

	public List<Seller> findall() {
		
		return dao.findAll();
	}
	 public void saveOrUpDate(Seller obj) { // este metodo e para inserir ou atualizar o departamento
		 
		if (obj.getId()==null) {
			dao.insert(obj);
			
		} else {
			dao.update(obj);

		} 
		 
		 
	 }
	public void remove (Seller obj) {
		
		dao.deleteById(obj.getId());
	}

}
