package com.tcs.household.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 单表通用的dao层的抽象类
 * @author xiaoj
 *
 * @param <T>
 */
public abstract class BaseDao<T> {

	protected abstract Mapper<T> getMapper();
	
	/**
	 * 根据id查询数据
	 * 
	 * @param id
	 * @return
	 */
	public T queryById(Long id) {
		return getMapper().selectByPrimaryKey(id);
	}
	
	/**
	 * 根据id查询数据
	 * 
	 * @param id
	 * @return
	 */
	public T queryById(Integer id) {
		return getMapper().selectByPrimaryKey(id);
	}

	/**
	 * 查询所有数据
	 * 
	 * @return
	 */
	public List<T> queryAll() {
		return getMapper().selectAll();
	}

	/**
	 * 根据条件查询一条数据，如果有多条数据会抛出异常
	 * 
	 * @param record
	 * @return
	 */
	public T queryOne(T record) {
		return getMapper().selectOne(record);
	}

	/**
	 * 根据条件查询一条数据，如果有多条数据会抛出异常
	 * 
	 * @param record
	 * @return
	 */
	public T queryOne(Example example) {
		return getMapper().selectOneByExample(example);
	}

	/**
	 * 根据条件查询一条数据，如果有多条数据会抛出异常
	 *
	 * @param record
	 * @return
	 */
	public List<T> query(Example example) {
		return getMapper().selectByExample(example);
	}

	/**
	 * 根据条件查询数据列表
	 * 
	 * @param record
	 * @return
	 */
	public List<T> queryListByWhere(T record) {
		return getMapper().select(record);
	}

	/**
	 * 根据字段(等号关系)，查询数量
	 * 
	 * @param record
	 * @return
	 */
	public Integer queryCount(T record) {
		return getMapper().selectCount(record);
	}

	/**
	 * 根据字段(等号关系)，查询数量
	 *
	 * @param e
	 * @return
	 */
	public Integer queryCount(Example e) {
		return getMapper().selectCountByExample(e);
	}

	/**
	 * 新增数据，返回成功的条数
	 * 
	 * @param record
	 * @return
	 */
	public Integer save(T record) {
		return getMapper().insert(record);
	}

	/**
	 * 新增数据，使用不为null的字段，返回成功的条数
	 * 
	 * @param record
	 * @return
	 */
	public Integer saveSelective(T record) {
		return getMapper().insertSelective(record);
	}

	/**
	 * 根据主键id，修改数据，返回成功的条数
	 * 
	 * @param record
	 * @return
	 */
	public Integer update(T record) {
		return getMapper().updateByPrimaryKey(record);
	}

	/**
	 * 修改数据，返回成功的条数
	 * 
	 * @param record
	 * @return
	 */
	public Integer updateByExample(T record, Example example) {
		return getMapper().updateByExample(record, example);
	}

	/**
	 * 根据主键id，修改数据，使用不为null的字段，返回成功的条数
	 * 
	 * @param record
	 * @return
	 */
	public Integer updateSelective(T record) {
		return getMapper().updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 修改数据，返回成功的条数
	 * 
	 * @param record
	 * @return
	 */
	public Integer updateByExampleSelective(T record, Example example) {
		return getMapper().updateByExampleSelective(record, example);
	}

	/**
	 * 根据id，删除数据
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteById(Long id) {
		return getMapper().deleteByPrimaryKey(id);
	}

	/**
	 * 根据ids，批量删除数据
	 * 
	 * @param clazz
	 * @param property
	 * @param values
	 * @return
	 */
	public Integer deleteByIds(Class<T> clazz, String property, List<Object> values) {
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, values);
		return getMapper().deleteByExample(example);
	}

	/**
	 * 根据条件，删除数据
	 * 
	 * @param record
	 * @return
	 */
	public Integer deleteByWhere(T record) {
		return getMapper().delete(record);
	}

	/**
	 * 根据条件，删除数据
	 *
	 * @param record
	 * @return
	 */
	public Integer deleteByExample(Example example) {
		return getMapper().deleteByExample(example);
	}
}
