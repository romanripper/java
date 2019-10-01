package ripper.dao;

import java.util.List;

public interface ExhibitionDao <Long,Exhibition>{
	void create(Exhibition entity);
    void delete(Exhibition entity);
    void update(Exhibition entity);
    Exhibition findById(Long key);
    List<Exhibition> findAll();

}
