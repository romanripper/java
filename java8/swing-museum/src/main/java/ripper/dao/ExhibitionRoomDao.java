package  ripper.dao;

import java.util.List;

public interface ExhibitionRoomDao <Long,ExhibitionRoom> {

	void create(ExhibitionRoom entity);
    void delete(ExhibitionRoom entity);
    void update(ExhibitionRoom entity);
    ExhibitionRoom findById(Long key);
    List<ExhibitionRoom> findAll();
}
