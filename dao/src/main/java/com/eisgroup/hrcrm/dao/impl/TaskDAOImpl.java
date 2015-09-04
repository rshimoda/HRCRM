package com.eisgroup.hrcrm.dao.impl;

import com.eisgroup.hrcrm.dao.TaskDAO;
import com.eisgroup.hrcrm.model.Candidate;
import com.eisgroup.hrcrm.model.PerformanceAppraisal;
import com.eisgroup.hrcrm.model.Task;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class TaskDAOImpl extends BaseObjectDAOImpl<Task> implements TaskDAO {

    private static final Logger LOG = LoggerFactory.getLogger(TaskDAOImpl.class);

    public TaskDAOImpl() {
        super(Task.class);
    }

    @Override
    public List<Task> getAll(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        LOG.info("getAll started, param: " + first + ":" + pageSize + ":" + sortField + ":" + sortOrder);
        long tStart = System.nanoTime();

        Query query;
        if ("assignee".equals(sortField) || "creator".equals(sortField)) {
            LOG.info("select p from Task p left join p." + sortField + " u " + getWhere(filters) + "  order by u.lastName " + (sortOrder.equals(SortOrder.ASCENDING) ? " asc, p.id asc " : " desc, p.id desc "));
            query = entityManager.createQuery("select p from Task p left join p." + sortField + " u " + getWhere(filters) + " order by lower(u.lastName) " + (sortOrder.equals(SortOrder.ASCENDING) ? " asc, p.id asc " : " desc, p.id desc ")).setFirstResult(first).setMaxResults(pageSize);
        } else {
            LOG.info("select p from Task p " + getWhere(filters) + getSortBy(sortField, sortOrder));
            query = entityManager.createQuery("select p from Task p " + getWhere(filters) + getSortBy(sortField, sortOrder)).setFirstResult(first).setMaxResults(pageSize);
        }

        List<Task> result = query.getResultList();
        LOG.info("getAll finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");
        return result;
    }


    @Override
    public List<Task> getByQuery(String queryS) {
        LOG.info("getByQuery started");
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery(queryS);
        List<Task> result = query.getResultList();
        LOG.info("getByQuery finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");
        return result;
    }

    public long count(Map<String, Object> filters) {
        LOG.info("count started");
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select count (p.id) from Task p " + getWhere(filters));
        long result = (Long) query.getSingleResult();
        LOG.info("count finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");

        return result;
    }

    @Override
    public List<String> getUsedComplexities() {
        LOG.info("getUsedComplexities started");
        long tStart = System.nanoTime();
        Query query = entityManager.createQuery("select distinct p.complexity.name from Task p order by p.complexity.name");
        List<String> result = query.getResultList();
        LOG.info("getUsedComplexities finished, time: " + (System.nanoTime() - tStart) / 1000000 + " ms");
        return result;
    }

    private String getSortBy(String sortField, SortOrder sortOrder) {
        if (sortField == null || sortOrder == null) {
            return "";
        }
        if ("TASK_TYPE".equals(sortField)) {
            return " order by TASK_TYPE " + (sortOrder.equals(SortOrder.ASCENDING) ? " asc, p.id asc " : " desc, p.id desc ");
        }
        if ("priority".equals(sortField)) {
            return " order by (case " +
                    "when p.priority='LOW' THEN 1 " +
                    "when p.priority='MEDIUM' THEN 2 " +
                    "when p.priority='HIGH' THEN 3 " +
                    "when p.priority='CRITICAL' THEN 4 " +
                    " end) " + (sortOrder.equals(SortOrder.ASCENDING) ? " asc, p.id asc " : " desc, p.id desc ");
        }
        if ("id".equals(sortField)) {
            return " order by " + (sortOrder.equals(SortOrder.ASCENDING) ? " p.id asc " : " p.id desc ");
        }
        return " order by p." + sortField + (sortOrder.equals(SortOrder.ASCENDING) ? " asc, p.id asc " : " desc, p.id desc ");
    }

    private String getWhere(Map<String, Object> filters) {
        if (filters == null || filters.isEmpty()) {
            return "";
        }
        if ((filters.containsKey("complexity") && ((String[]) filters.get("complexity")).length > 0) ||
                (filters.containsKey("priority") && ((String[]) filters.get("priority")).length > 0)) {
            String[] stubArray = {PerformanceAppraisal.class.getSimpleName(), Candidate.class.getSimpleName()};
            filters.put("stubMode", stubArray);
        }

        boolean isFirstElement = true;
        StringBuilder filter = new StringBuilder();
        for (String filterKey : filters.keySet()) {
            switch (filterKey) {
                case "TASK_TYPE": {
                    String inString = "";
                    if (((String[]) filters.get(filterKey)).length == 0) {
                        continue;
                    }

                    for (String string : (String[]) filters.get(filterKey)) {
                        inString += " '" + string + "',";
                    }
                    inString = inString.substring(0, inString.length() - 1);
                    if (isFirstElement) {
                        appendStringArrayArg(filter, "TASK_TYPE ", inString);
                    } else {
                        appendStringArrayArg(filter, " and TASK_TYPE ", inString);
                    }
                }
                break;

                case "stubMode": {
                    String inString = "";
                    if (((String[]) filters.get(filterKey)).length == 0) {
                        continue;
                    }

                    for (String string : (String[]) filters.get(filterKey)) {
                        inString += " '" + string + "',";
                    }
                    inString = inString.substring(0, inString.length() - 1);
                    if (isFirstElement) {
                        appendStringArrayArg(filter, "TASK_TYPE not ", inString);
                    } else {
                        appendStringArrayArg(filter, " and TASK_TYPE not ", inString);
                    }
                }
                break;

                case "statusActive": {
                    if (isFirstElement) {
                        appendNotStringArg(filter, "p.status", String.valueOf(filters.get(filterKey)));
                    } else {
                        appendNotStringArg(filter, " and p.status", String.valueOf(filters.get(filterKey)));
                    }
                }
                break;
                case "status": {
                    String inString = "";
                    if (((String[]) filters.get(filterKey)).length == 0) {
                        continue;
                    }

                    for (String string : (String[]) filters.get(filterKey)) {
                        inString += " '" + string + "',";
                    }
                    inString = inString.substring(0, inString.length() - 1);
                    if (isFirstElement) {
                        appendStringArrayArg(filter, "p.status", inString);
                    } else {
                        appendStringArrayArg(filter, " and p.status", inString);
                    }
                }
                break;
                case "priority": {
                    String inString = "";
                    if (((String[]) filters.get(filterKey)).length == 0) {
                        continue;
                    }

                    for (String string : (String[]) filters.get(filterKey)) {
                        inString += " '" + string + "',";
                    }
                    inString = inString.substring(0, inString.length() - 1);
                    if (isFirstElement) {
                        appendStringArrayArg(filter, "p.priority", inString);
                    } else {
                        appendStringArrayArg(filter, " and p.priority", inString);
                    }
                }
                break;
                case "id": {
                    try {
                        if (isFirstElement) {
                            appendLongArg(filter, "p." + filterKey, String.valueOf(filters.get(filterKey)));
                        } else {
                            appendLongArg(filter, " and p." + filterKey, String.valueOf(filters.get(filterKey)));
                        }
                    } catch (NumberFormatException e) {
                        LOG.info("got NumberFormatException");
                        continue;

                    }
                }
                break;
                case "summary": {
                    if (isFirstElement) {
                        appendStringArg(filter, "p." + filterKey, String.valueOf(filters.get(filterKey)));
                    } else {
                        appendStringArg(filter.append(" and "), "p." + filterKey, String.valueOf(filters.get(filterKey)));
                    }
                }
                break;

                case "complexity": {
                    String inString = "";
                    if (((String[]) filters.get(filterKey)).length == 0) {
                        continue;
                    }

                    for (String string : (String[]) filters.get(filterKey)) {
                        inString += " '" + string + "',";
                    }
                    inString = inString.substring(0, inString.length() - 1);
                    if (isFirstElement) {
                        appendStringArrayArg(filter, "p.complexity.name", inString);
                    } else {
                        appendStringArrayArg(filter, " and p.complexity.name", inString);
                    }
                }
                break;

                case "dueDate":
                case "creationDate": {
                    if (isFirstElement) {
                        appendDateArg(filter, "p." + filterKey, (Date) filters.get(filterKey));
                    } else {
                        appendDateArg(filter, " and p." + filterKey, (Date) filters.get(filterKey));
                    }
                }
                break;

                default:
            }
            isFirstElement = false;
        }
        if (filter.length() != 0) {
            filter.insert(0, " where ");
        }
        return filter.toString();
    }

    private void appendExactArg(StringBuilder query, String key, String type) {
        query.append(" ").append(key).append(" = '").append(type).append("' ");
    }

    private void appendStringArg(StringBuilder query, String key, String arg) {
        String ar = arg.replace("%", "\\%");
        query.append(" lower(" + key + ")").append(" like lower('%").append(ar.trim()).append("%') ");
    }

    private void appendStringArrayArg(StringBuilder query, String key, String arg) {
        query.append(" ").append(key).append(" in (").append(arg).append(") ");
    }

    private void appendNotStringArg(StringBuilder query, String key, String arg) {
        query.append(" ").append(key).append(" != '").append(arg).append("' ");
    }

    private void appendLongArg(StringBuilder query, String key, String arg) throws NumberFormatException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(key).append(" like '").append(Long.parseLong(arg.trim())).append("%' ");
        query.append(stringBuilder);
    }

    private void appendDateArg(StringBuilder query, String key, Date arg) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date;
        date = fromFormat.format(arg);
//            query.append(key).append(" = '").append(date).append("' ");
        query.append(key).append(" = ").append("to_date('").append(date).append("','dd-MM-yyyy') ");
    }

}
