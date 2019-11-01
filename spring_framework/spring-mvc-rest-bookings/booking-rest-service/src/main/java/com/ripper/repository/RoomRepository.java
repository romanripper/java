package com.ripper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ripper.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

	@Query("select r from Room r where r.roomName = :name")
	Room findByName(@Param("name") String name);

	@Transactional
	@Modifying
	void deleteByRoomName(String name);
}
